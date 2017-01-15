package test.j.jave.kernal.streaming.netty;

import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
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
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.bunny.modular._p.streaming.netty.client.NioChannelRunnable;
import me.bunny.modular._p.streaming.netty.client.Request;
import me.bunny.modular._p.streaming.netty.client.RequestMeta;
import me.bunny.modular._p.streaming.netty.client.SimpleHttpClientInitializer;

public class ATest {
	
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
	        AttributeKey<String> POOL_KEY = 
	        		AttributeKey.newInstance("channelPool");
	        b.attr(POOL_KEY, "bbbb");
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new SimpleHttpClientInitializer(sslCtx));
            // Make the connection attempt.
            ChannelFuture channelFuture=b.remoteAddress("127.0.0.1", 8080).clone().connect()
            ;		
            Channel channel=channelFuture.channel();
            RequestMeta requestMeta=new RequestMeta();
			requestMeta.setContent(("test-"+0).getBytes(Charset.forName("utf-8")));
			requestMeta.setUrl("http://127.0.0.1:8080/DataRemote/admin/galaxy/index");
			Request request=Request.post(requestMeta);
			
			
//            new NioChannelRunnable(request).request(channel);
//	        ch.clo
            Thread.sleep(1000000);
            
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
