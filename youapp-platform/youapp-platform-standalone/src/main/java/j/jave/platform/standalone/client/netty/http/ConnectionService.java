package j.jave.platform.standalone.client.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
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
import j.jave.kernal.dataexchange.channel.JMessage;
import j.jave.kernal.dataexchange.modelprotocol.JMessageHeadNames;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.sync.JSyncMonitor;
import j.jave.kernal.jave.sync.JSyncMonitorRegisterService;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.standalone.data.MessageMeta.MessageMetaNames;

import java.net.URI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

class ConnectionService {

	private final static JDefaultHashCacheService defaultHashCacheService=
			JServiceHubDelegate.get().getService(new Object(), JDefaultHashCacheService.class);
	
	private final static JSyncMonitorRegisterService syncMonitorRegisterService=
			JServiceHubDelegate.get().getService(new Object(), JSyncMonitorRegisterService.class);
	
	private static ConcurrentHashMap<String, ConnectionService> 
	connectionServices=new ConcurrentHashMap<String, ConnectionService>();
	
	private String url;
	
	private String host; 
	
	private int port;
	
	private URI uri=null;
	
	public ConnectionService(String url) {
		super();
		this.url = url;
	}
	
	private static final ReentrantLock initializeConnectionLock=new ReentrantLock();
	
	public static ConnectionService get(String url) throws Exception{
		
		URI uri=new URI(url);
		String unique=getScheme(uri)+"//"+getHost(uri)+":"+getPort(uri);
		
		if(connectionServices.containsKey(unique)){
			return connectionServices.get(unique);
		}
		initializeConnectionLock.lockInterruptibly();
		try{
			ConnectionService connectionService=  new ConnectionService(url);
			connectionServices.put(unique, connectionService);
			return connectionService;
		}finally{
			if(initializeConnectionLock.isHeldByCurrentThread()){
				initializeConnectionLock.unlock();
			}
		}
	}

	// Make the connection attempt.
    private Channel ch =null;
    
    private static class ChannelFutureSync implements JSyncMonitor{
    	
    	private String unique;
    	
    	public ChannelFutureSync() {
		}
    	
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
    
    /**
     * request and return the reply to the request.
     * @param message
     * @param bytes  the content bytes
     * @return
     */
    public byte[] request(JMessage message,byte[] bytes) throws Exception{
    	// Prepare the HTTP request.
    	DefaultFullHttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.POST, uri.getRawPath(),Unpooled.wrappedBuffer(bytes));
//    	request.content().capacity(bytes.length).setBytes(0, bytes);
        request.headers().set(HttpHeaderNames.HOST, host);
//        request.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP);
        request.headers().set(JMessageHeadNames.DATA_ENCODER, 
        		message.getDataByteEncoder()
        		);
        request.headers().set(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
        final String requestId=JUniqueUtils.unique();
        request.headers().set(MessageMetaNames.CONVERSATION_ID, requestId);
        
        // Set some example cookies.
        request.headers().set(
                HttpHeaderNames.COOKIE,
                io.netty.handler.codec.http.cookie.ClientCookieEncoder.LAX.encode(
                        new io.netty.handler.codec.http.cookie.DefaultCookie("my-cookie", "foo"),
                        new io.netty.handler.codec.http.cookie.DefaultCookie("another-cookie", "bar")));
        
        // Send the HTTP request.
        ChannelFuture channelFuture= ch.writeAndFlush(request);
//        channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
//        	public void operationComplete(Future<? super Void> future) throws Exception {
//        		String object=(String) defaultHashCacheService.get(requestId);
//        		sync.bytes=object.getBytes("utf-8");
//        		synchronized (sync) {
//        			sync.notifyAll();
//				}
//        		
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
    
    private static int getPort(URI uri){
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
    
    private static String getScheme(URI uri){
    	return uri.getScheme() == null? "http" : uri.getScheme();
    }
    
    private static String getHost(URI uri){
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