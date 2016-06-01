package j.jave.platform.standalone.client.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import j.jave.kernal.dataexchange.channel.Message;
import j.jave.kernal.dataexchange.modelprotocol.JProtocolConstants;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.net.URI;

class ConnectionService {

	private final static JDefaultHashCacheService defaultHashCacheService=
			JServiceHubDelegate.get().getService(new Object(), JDefaultHashCacheService.class);
	
	private String url;
	
	private String host; 
	
	private int port;
	
	private URI uri=null;
	
	public ConnectionService(String url) {
		super();
		this.url = url;
	}
	
	
	public static ConnectionService get(String url){
		return new ConnectionService(url);
	}

	// Make the connection attempt.
    private Channel ch =null;
    
    private static class ReturnObject{
    	
    	private byte[] bytes;
    	
    }
    
    /**
     * request and return the reply to the request.
     * @param message
     * @param bytes  the content bytes
     * @return
     */
    public byte[] request(Message message,byte[] bytes){
    	
    	ByteBuf byteBuf=ch.alloc().buffer();
    	byteBuf.setBytes(0, bytes);
    	// Prepare the HTTP request.
    	DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath(),byteBuf);
        request.headers().set(HttpHeaderNames.HOST, host);
        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
        request.headers().set(JProtocolConstants.PROTOCOL_HEAD, "JSON");
        final String requestId=JUniqueUtils.unique();
        request.headers().set("request-unique-id", requestId);
        
        // Set some example cookies.
        request.headers().set(
                HttpHeaderNames.COOKIE,
                io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(
                        new io.netty.handler.codec.http.cookie.DefaultCookie("my-cookie", "foo"),
                        new io.netty.handler.codec.http.cookie.DefaultCookie("another-cookie", "bar")));
        
        // Send the HTTP request.
        final ReturnObject sync=new ReturnObject();
        ChannelFuture channelFuture= ch.writeAndFlush(request);
        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
        	public void operationComplete(Future<? super Void> future) throws Exception {
        		String object=(String) defaultHashCacheService.get(requestId);
        		sync.bytes=object.getBytes("utf-8");
        		synchronized (sync) {
        			sync.notifyAll();
				}
        		
        	};
		});
        synchronized (sync) {
        	try {
				sync.wait();
				System.out.println("wakeup...");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        return sync.bytes;
    }
    
    
    public void connect() throws Exception {
        uri = new URI(url);
        String scheme = uri.getScheme() == null? "http" : uri.getScheme();
        host = uri.getHost() == null? "127.0.0.1" : uri.getHost();
        port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }

        if (!"http".equalsIgnoreCase(scheme) && !"https".equalsIgnoreCase(scheme)) {
            System.err.println("Only HTTP(S) is supported.");
            return;
        }

        // Configure SSL context if necessary.
        final boolean ssl = "https".equalsIgnoreCase(scheme);
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new HttpSnoopClientInitializer(sslCtx));

            // Make the connection attempt.
            ch = b.connect(host, port).sync().channel();

            

            // Wait for the server to close the connection.
//            ch.closeFuture().sync();
        } finally {
            // Shut down executor threads to exit.
//            group.shutdownGracefully();
        }
    }
}