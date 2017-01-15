package j.jave.platform.standalone.client.netty.http;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import me.bunny.kernel._c.base64.JBase64;
import me.bunny.kernel._c.base64.JBase64FactoryProvider;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.support.JDefaultHashCacheService;
import me.bunny.kernel._c.sync.JDefaultSyncMonitor;
import me.bunny.kernel._c.sync.JSyncConfig;
import me.bunny.kernel._c.sync.JSyncMonitor;
import me.bunny.kernel._c.sync.JSyncMonitorRegisterService;
import me.bunny.kernel._c.sync.JSyncMonitorWakeupEvent;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.utils.JUniqueUtils;
import me.bunny.kernel.dataexchange.channel.JMessage;
import me.bunny.kernel.dataexchange.impl.JMessageHeadNames;
import me.bunny.kernel.dataexchange.model.DefaultMessageMeta;
import me.bunny.kernel.dataexchange.model.MessageMeta.MessageMetaNames;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.net.URI;
import java.util.Map;

class DefaultConnectionService {

	private JLogger logger=JLoggerFactory.getLogger(DefaultConnectionService.class);
	
	private final static JDefaultHashCacheService defaultHashCacheService=
			JServiceHubDelegate.get().getService(new Object(), JDefaultHashCacheService.class);
	
	private final static JSyncMonitorRegisterService syncMonitorRegisterService=
			JServiceHubDelegate.get().getService(new Object(), JSyncMonitorRegisterService.class);
	
	private final static JBase64 BASE64=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	private String url;
	
	private String host; 
	
	private int port;
	
	private URI uri=null;
	
	// Make the connection attempt.
    private volatile Channel ch =null;
	
    private volatile String asyncChannelActiveNotifyUnique;
    
	DefaultConnectionService(String url) {
		this.url = url;
	}
	
	private class HttpSnoopClientInitializer extends ChannelInitializer<SocketChannel> {

	    private final SslContext sslCtx;

	    public HttpSnoopClientInitializer(SslContext sslCtx) {
	        this.sslCtx = sslCtx;
	    }

	    @Override
	    public void initChannel(SocketChannel ch) {
	        ChannelPipeline p = ch.pipeline();

	        // Enable HTTPS if necessary.
	        if (sslCtx != null) {
	            p.addLast(sslCtx.newHandler(ch.alloc()));
	        }

	        p.addLast(new HttpClientCodec());

	        // Remove the following line if you don't want automatic content decompression.
	        p.addLast(new HttpContentDecompressor());

	        // Uncomment the following line if you don't want to handle HttpContents.
	        //p.addLast(new HttpObjectAggregator(1048576));

	        p.addLast(new HttpSnoopClientHandler());
	        
	        p.addLast(new ChannelInboundHandlerAdapter(){
	        	@Override
	        	public void channelActive(ChannelHandlerContext ctx)
	        			throws Exception {
	        		super.channelActive(ctx);
	        		if(JStringUtils.isNotNullOrEmpty(asyncChannelActiveNotifyUnique)){
	        			syncMonitorRegisterService.wakeup(new JSyncMonitorWakeupEvent(this, asyncChannelActiveNotifyUnique));
	        		}
	        	}
	        });
	    }
	}
	
	/**
	 * await until the channel is active before timeout.
	 * @return
	 * @see DefaultConnectionService#isActive()
	 */
	public DefaultConnectionService await(int timeout){
		
		if(isActive()){
			return this;
		}
		boolean forceWait=timeout<1;
		do{
			final JDefaultSyncMonitor defaultSyncMonitor=new JDefaultSyncMonitor();
			this.asyncChannelActiveNotifyUnique=defaultSyncMonitor.unique();
	        syncMonitorRegisterService.sync(defaultSyncMonitor,new JSyncConfig(timeout));
		}while(forceWait);

		if(isActive()){
        	return this;
        }
        else{
        	throw new IllegalStateException("Channel is not active.");
        }
	}
	
	/**
	 * throw {@link IllegalStateException} if channel is not connected or registered.
	 * @throws IllegalStateException 
	 * @return
	 */
	boolean isActive(){
		if(ch==null){
			throw new IllegalStateException("Channel is not connected/registered.");
		}
		else{
			return ch.isActive();
		}
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
        if((headVal=heads.get(HttpHeaderNames.CONNECTION.toString()))!=null){
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
            	byte[] realDataBytes=BASE64.decodeBase64(base64Str);
            	
            	s=new String(realDataBytes,"utf-8");
            	base64Str=JJSON.get().parse(s, DefaultMessageMeta.class).getData();
            	realDataBytes=BASE64.decodeBase64(base64Str);
            
            	logger.debug("client-requset-content-inner-data ["+requestId+"] : "+new String(realDataBytes,"utf-8"));
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
        ResponseContext responseContext=(ResponseContext) defaultHashCacheService.get(requestId);
        return responseContext.getRealData().getBytes("utf-8");
    }
    
	public void connect() throws Exception {
		retryConnect();
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