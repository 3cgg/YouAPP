package test.j.jave.kernal.streaming.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.pool.ChannelPoolHandler;
import io.netty.channel.pool.SimpleChannelPool;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import j.jave.kernal.streaming.netty.client.HttpSnoopClientInitializer;

public class ClientTest {

	public static void main(String[] args) {
		try{

			final SslContext sslCtx;
	        if (false) {
	            sslCtx = SslContextBuilder.forClient()
	                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
	        } else {
	            sslCtx = null;
	        }
	        
	     // Configure the client.
	        EventLoopGroup group = new NioEventLoopGroup();
	        Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
//             .handler(new HttpSnoopClientInitializer(sslCtx))
             ;
            // Make the connection attempt.
            b.remoteAddress("127.0.0.1", 8080);
            
            SimpleChannelPool channelPool=new SimpleChannelPool(b, new ChannelPoolHandler() {
				
				@Override
				public void channelReleased(Channel ch) throws Exception {
					System.out.println("channelReleased "+ch);
				}
				
				@Override
				public void channelCreated(Channel ch) throws Exception {
					ch.pipeline().addLast(new HttpSnoopClientInitializer(sslCtx));
					System.out.println("channelCreated "+ch);
				}
				
				@Override
				public void channelAcquired(Channel ch) throws Exception {
					System.out.println("channelAcquired "+ch);
				}
			});
//	        ch.clo
            for(int i=0;i<10;i++){
            	Future<Channel> future= channelPool.acquire();
            	future.addListener(new GenericFutureListener<Future<? super Channel>>() {
					@Override
					public void operationComplete(Future<? super Channel> future) throws Exception {
						System.out.println(future);
					}
				});
            	Channel channel=future.get();
            	DefaultFullHttpRequest fullHttpRequest = new DefaultFullHttpRequest(
                        HttpVersion.HTTP_1_1, HttpMethod.POST, "/aaa",
                        Unpooled.wrappedBuffer("aaaaaaaaaaaaaa".getBytes()));
            	channel.writeAndFlush(fullHttpRequest);
            	System.out.println(future);
            	channelPool.release(future.get());
    		}
            
            Thread.sleep(1000000);
            
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}
