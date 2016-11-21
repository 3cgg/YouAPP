package j.jave.kernal.streaming.netty.client;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;


public class NioChannelExecutor implements ChannelExecutor<NioChannelRunnable>{

	private static final JLogger LOGGER = JLoggerFactory.getLogger(NioChannelExecutor.class);

	private final String host;

	private final int port;

	private final boolean useSSL;

	private SimpleChannelPool channelPool;

	public NioChannelExecutor(String host, int port, boolean useSSL) {
		this.host = host;
		this.port = port;
		this.useSSL = useSSL;
		prepare();
	}

	public NioChannelExecutor(String host, int port) {
		this(host, port, false);
	}

	private void prepare() {
		try {
			final SslContext sslCtx;
			if (useSSL) {
				sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
			} else {
				sslCtx = null;
			}
			// Configure the client.
			EventLoopGroup group = new NioEventLoopGroup();
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
//					.handler(new HttpSnoopClientInitializer(sslCtx))
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
					ch.pipeline().addLast(new SimpleHttpClientInitializer(sslCtx));
				}

				@Override
				public void channelAcquired(Channel ch) throws Exception {
					if (LOGGER.isDebugEnabled()) {
						LOGGER.debug("channelAcquired " + ch);
					}
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	private static ExecutorService executorService=Executors.newFixedThreadPool(10);
	 
	@SuppressWarnings("rawtypes")
	private static GenericPromiseListener DO=new GenericPromiseListener() {
		
		@Override
		public void operationComplete(final CallPromise callPromise) throws Exception {
			DefaultCallPromise defaultCallPromise=(DefaultCallPromise) callPromise;
			if(defaultCallPromise.isResponsed()){
				executorService.execute(new Runnable() {
					@Override
					public void run() {
						defaultCallPromise.getChannelRunnable()
							.response(defaultCallPromise._getResponse());
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
		try{
			Future<Channel> channelFuture= channelPool.acquire();
			while(channel==null){
				try{
					channel=channelFuture.get();
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
			callPromise.setFuture(cf);
			cf.addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					ChannelFuture channelFuture=(ChannelFuture) future;
					if(channelFuture.isSuccess()){
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
			throw new RuntimeException(e);
		}
	}

}
