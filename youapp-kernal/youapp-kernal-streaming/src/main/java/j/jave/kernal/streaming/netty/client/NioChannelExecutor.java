package j.jave.kernal.streaming.netty.client;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.concurrent.Future;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

public class NioChannelExecutor implements ChannelExecutor<NioChannelRunnable>{

	private JLogger logger = JLoggerFactory.getLogger(NioChannelExecutor.class);

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
					if (logger.isDebugEnabled()) {
						logger.debug("channelReleased " + ch);
					}
				}

				@Override
				public void channelCreated(Channel ch) throws Exception {
					if (logger.isDebugEnabled()) {
						logger.debug("channelCreated " + ch);
					}
					ch.pipeline().addLast(new HttpSnoopClientInitializer(sslCtx));
				}

				@Override
				public void channelAcquired(Channel ch) throws Exception {
					if (logger.isDebugEnabled()) {
						logger.debug("channelAcquired " + ch);
					}
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public <V> Future<V> execute(NioChannelRunnable channelRunnable) {
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
			channelRunnable.run(channel);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			if(channel!=null){
				channelPool.release(channel);
			}
		}
		return null;
	}

}
