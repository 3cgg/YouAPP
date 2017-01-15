package j.jave.kernal.streaming.netty.client;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.bunny.kernel._c.exception.JNestedRuntimeException;
import me.bunny.kernel._c.exception.JOperationNotSupportedException;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;


public class NioChannelExecutor implements ChannelExecutor<NioChannelRunnable>{

	private static final JLogger LOGGER = JLoggerFactory.getLogger(NioChannelExecutor.class);

	private final String host;

	private final int port;

	private SimpleChannelPool channelPool;
	
	private ChannelInitializer<? extends Channel> clientInitializer;

	public NioChannelExecutor(String host, int port) {
		this.host = host;
		this.port = port;
	}

	NioChannelExecutor addChannelInitializer(ChannelInitializer<? extends Channel> clientInitializer){
		this.clientInitializer=clientInitializer;
		return this;
	}
	
	NioChannelExecutor connect() {
		try {
			// Configure the client.
			EventLoopGroup group = new NioEventLoopGroup(5,new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					return new Thread(r,"netty-client-io");
				}
			});
			Bootstrap b = new Bootstrap();
			b.group(group)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.channel(NioSocketChannel.class)
						.remoteAddress(host, port);
			channelPool = new SimpleChannelPool(b, new ChannelPoolHandler() {
				@Override
				public void channelReleased(Channel ch) throws Exception {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("channelReleased " + ch);
					}
				}

				@Override
				public void channelCreated(Channel ch) throws Exception {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("channelCreated " + ch);
					}
					ch.pipeline().addLast(clientInitializer);
				}

				@Override
				public void channelAcquired(Channel ch) throws Exception {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("channelAcquired " + ch);
					}
				}
			});
		} catch (Exception e) {
			throw new JNestedRuntimeException(e);
		}
		return this;
	}
	private static ExecutorService executorService=Executors.newFixedThreadPool(10);
	 
	@SuppressWarnings("rawtypes")
	private static GenericPromiseListener DO=new GenericPromiseListener() {
		
		@Override
		public void operationComplete(final CallPromise callPromise) throws Exception {
			final DefaultCallPromise defaultCallPromise=(DefaultCallPromise) callPromise;
			if(defaultCallPromise.isResponsed()){
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						defaultCallPromise.getChannelRunnable()
							.response(defaultCallPromise._getResponse());
					}
				});
			}else if(defaultCallPromise.isDone()
					&&defaultCallPromise.cause()!=null){
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						defaultCallPromise.getChannelRunnable()
							.fail(defaultCallPromise.cause());
					}
				});
			}else if(defaultCallPromise.isCancelled()){
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						defaultCallPromise.getChannelRunnable()
							.cancelled(null);
					}
				});
			}
			
		}
		
	};
	
	
	private ReleaseChannelInboundHandler ONE=new ReleaseChannelInboundHandler();
	
	@Sharable
	private class ReleaseChannelInboundHandler extends ChannelInboundHandlerAdapter{
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			try{
				ctx.channel().pipeline().remove(this);
				channelPool.release(ctx.channel());
			}finally{
				super.channelReadComplete(ctx);
			}
		}
	}
	
	@SuppressWarnings({"rawtypes","unchecked"})
	private class ReturnResponseInboundHandler extends OnceChannelInboundHandler{
		
		private final DefaultCallPromise callPromise;
		
		public ReturnResponseInboundHandler(DefaultCallPromise callPromise) {
			this.callPromise=callPromise;
		}
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			callPromise.setResponse(msg);
			super.channelRead(ctx, msg);
		}
		
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
			super.channelReadComplete(ctx);
		}
	}
	
	
	@Override
	public <V> CallPromise<V> execute(NioChannelRunnable channelRunnable) throws Exception {

		Channel channel=null;
		short count=0;
		try{
			Future<Channel> channelFuture= channelPool.acquire();
			while(channel==null){
				try{
					if(channelFuture.isDone()){
						if(channelFuture.isSuccess()){
							channel=channelFuture.get();
						}
						else{
							if(LOGGER.isDebugEnabled()){
								LOGGER.debug(channelFuture.cause().getMessage(),channelFuture.cause());
							}
							channelFuture= channelPool.acquire();
							count++;
							if(count>3){
								Throwable throwable=null;
								while(true){
									if(channelFuture.isDone()){
										throwable=channelFuture.cause();
										break;
									}
								}
								LOGGER.error(throwable.getMessage(), throwable);
								throw new JNestedRuntimeException(throwable);
							}
						}
					}
					
				}catch (CancellationException e) {
				}catch (ExecutionException e) {
					throw e;
				}catch (InterruptedException	 e) {
				}
			}
			final DefaultCallPromise<V> callPromise=new DefaultCallPromise<>(channelRunnable);
			callPromise.addListener(DO);
			ReturnResponseInboundHandler handler=new ReturnResponseInboundHandler(callPromise);
			channel.pipeline().addLast(handler);
			channel.pipeline().addLast(ONE);
//			ReleaseChannelInboundHandler exists= channel.pipeline().get(ReleaseChannelInboundHandler.class);
//			if(exists==null){			}
			ChannelFuture cf= channelRunnable.request(channel);
			callPromise.setChannelFuture(cf);
//			if(cf instanceof ChannelPromise){
//				ChannelPromise channelPromise=(ChannelPromise) cf;
//				channelPromise.setUncancellable();
//			}
			cf.addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					ChannelFuture channelFuture=(ChannelFuture) future;
					if(channelFuture.isSuccess()){
						callPromise.setRequestUncancellable();
						callPromise.setRequestSuccess();
					}else  if(channelFuture.isCancelled()){
						callPromise.setRequestCancelled();
					}
					
				}
			});
			return callPromise;
		}catch (Exception e) {
			if(channel!=null){
				channel.close();
			}
			throw new JNestedRuntimeException(e);
		}
	}

	ChannelInitializer<? extends Channel> getClientInitializer() {
		return clientInitializer;
	}
	
	String getHost() {
		return host;
	}
	
	int getPort() {
		return port;
	}
	
	@Override
	public String uri() {
		throw new JOperationNotSupportedException("impossible to get uri, missing sufficient message.");
	}

	@Override
	public void close() throws IOException {
		channelPool.close();
	}
}
