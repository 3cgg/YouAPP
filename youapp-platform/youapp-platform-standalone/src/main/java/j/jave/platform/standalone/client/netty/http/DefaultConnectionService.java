package j.jave.platform.standalone.client.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
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
import j.jave.kernal.dataexchange.channel.JMessage;
import j.jave.kernal.dataexchange.impl.JMessageHeadNames;
import j.jave.kernal.dataexchange.model.DefaultMessageMeta;
import j.jave.kernal.dataexchange.model.MessageMeta.MessageMetaNames;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.sync.JSyncMonitor;
import j.jave.kernal.jave.sync.JSyncMonitorRegisterService;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

class DefaultConnectionService {

	private JLogger logger=JLoggerFactory.getLogger(DefaultConnectionService.class);
	
	private final static JDefaultHashCacheService defaultHashCacheService=
			JServiceHubDelegate.get().getService(new Object(), JDefaultHashCacheService.class);
	
	private final static JSyncMonitorRegisterService syncMonitorRegisterService=
			JServiceHubDelegate.get().getService(new Object(), JSyncMonitorRegisterService.class);
	
	private String url;
	
	private String host; 
	
	private int port;
	
	private URI uri=null;
	
	// Make the connection attempt.
    private Channel ch =null;
	
	private DefaultConnectionService(String url) {
		this.url = url;
	}
	
	public static DefaultConnectionService get(String url) throws Exception{
		return new DefaultConnectionService(url);
	}
	
    
    private static class ChannelFutureSync implements JSyncMonitor{
    	
    	private String unique;
    	
    	public ChannelFutureSync(String unique) {
    		this.unique=unique;
    	}

		@Override
		public String unique() {
			return unique;
		}

		@Override
		public String name() {
			return unique;
		}
    	
    }
    
	public byte[] request(JMessage message,Map<String, Object> heads,byte[] bytes) throws Exception{
    	// Prepare the HTTP request.
    	DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.POST, uri.getRawPath(),Unpooled.wrappedBuffer(bytes));
        request.headers().set(HttpHeaderNames.HOST, host);
        Object headVal=null;
        //is keep-alive
        if((headVal=heads.get(HttpHeaderNames.CONNECTION))!=null){
        	request.headers().set(HttpHeaderNames.CONNECTION, String.valueOf(headVal));
        }
        
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
        request.headers().set(JMessageHeadNames.DATA_EXCHNAGE_IDENTIFIER, 
        		JMessage.class.getName()
        		);

        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
        final String requestId=JUniqueUtils.unique();
        request.headers().set(MessageMetaNames.CONVERSATION_ID, requestId);
        
        if(logger.isDebugEnabled()){
        	try{
        		String s=new String(bytes,"utf-8");
            	logger.debug("client-requset-content ["+requestId+"] : "+s);
            	String base64Str=JJSON.get().parse(s, JMessage.class).getData();
            	byte[] realDataBytes=JBase64FactoryProvider.getBase64Factory().getBase64().decodeBase64(base64Str);
            	
            	s=new String(realDataBytes,"utf-8");
            	base64Str=JJSON.get().parse(s, DefaultMessageMeta.class).getData();
            	realDataBytes=JBase64FactoryProvider.getBase64Factory().getBase64().decodeBase64(base64Str);
            
            	logger.debug("request real data->"+new String(realDataBytes,"utf-8"));
        	}catch(Exception e){
        	}
        }
        
        // Set some example cookies.
        request.headers().set(
                HttpHeaderNames.COOKIE,
                io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(
                        new io.netty.handler.codec.http.cookie.DefaultCookie("my-cookie", "foo"),
                        new io.netty.handler.codec.http.cookie.DefaultCookie("another-cookie", "bar")));
        
        // Send the HTTP request.
        ch.writeAndFlush(request);
//        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
//        	public void operationComplete(Future<? super Void> future) throws Exception {
//        		String object=(String) defaultHashCacheService.get(requestId);
//        		sync.bytes=object.getBytes("utf-8");
//        		synchronized (sync) {
//        			sync.notifyAll();
//				}
//        	};
//		});
        final ChannelFutureSync sync=new ChannelFutureSync(requestId);
        syncMonitorRegisterService.sync(sync, defaultHashCacheService);
        return ((String)defaultHashCacheService.get(requestId)).getBytes("utf-8");
    }
    
    private ReentrantLock lock=new ReentrantLock();
    
	public void connect() throws Exception {
    	
    	if(ch==null
    			||(ch!=null&&!ch.isActive())){
    		lock.lockInterruptibly();
    		try{
    			retryConnect();
    		}finally{
    			if(lock.isHeldByCurrentThread()){
    				lock.unlock();
    			}
    		}
    	}
    }
    
	public static int getPort(URI uri){
    	String scheme = getScheme(uri);
    	int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(scheme)) {
                port = 443;
            }
        }
        return port;
    }
    
    public static String getScheme(URI uri){
    	return uri.getScheme() == null? "http" : uri.getScheme();
    }
    
    public static String getHost(URI uri){
    	return uri.getHost() == null? "127.0.0.1" : uri.getHost();
    }
    
    private void retryConnect() throws Exception {
        uri = new URI(url);
        String scheme =  getScheme(uri);
        host = getHost(uri);
        port = getPort(uri);

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