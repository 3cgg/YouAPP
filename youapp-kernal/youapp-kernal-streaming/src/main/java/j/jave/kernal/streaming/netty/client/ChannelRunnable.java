package j.jave.kernal.streaming.netty.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import j.jave.kernal.jave.utils.JStringUtils;

public abstract class ChannelRunnable {
	
	private final Request request;
	
	private final ChannelResponseCall responseCall;
	
	public ChannelRunnable(Request request,ChannelResponseCall responseCall) {
		this.request = request;
		this.responseCall=responseCall;
	}
	
	public final void response(Object object){
		try{
			responseCall.run(request, object);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public final ChannelFuture request(Channel channel){
		try{
			DefaultFullHttpRequest fullHttpRequest=prepare();
			ChannelFuture channelFuture= doRequest(channel,fullHttpRequest);
//			channelFuture.addListener(new GenericFutureListener<Future<? super Void>>() {
//				@Override
//				public void operationComplete(Future<? super Void> future) throws Exception {
//					System.out.println("complete....");
//				}
//			});
			return channelFuture;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	abstract protected ChannelFuture doRequest(Channel channel,DefaultFullHttpRequest fullHttpRequest)throws Exception;
	
	/**
	 * 
	 * @return
	 */
	protected DefaultFullHttpRequest prepare(){
		checkHeader();
		URI uri=uri();
		// Prepare the HTTP request.
    	DefaultFullHttpRequest fullHttpRequest = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, httpMethod(), uri.getRawPath(),
                Unpooled.wrappedBuffer(content()));
    	fullHttpRequest.headers().set(HttpHeaderNames.HOST, uri.getHost());
        //is keep-alive
        if(isKeepAlive()){
        	fullHttpRequest.headers().set(HttpHeaderNames.CONNECTION, 
        			HttpHeaderValues.KEEP_ALIVE);
        }
        if(JStringUtils.isNotNullOrEmpty(acceptEncoding())){
        	fullHttpRequest.headers().set(HttpHeaderNames.ACCEPT_ENCODING, 
            		HttpHeaderValues.GZIP);
        }
        fullHttpRequest.headers().set(HttpHeaderNames.CONTENT_LENGTH, 
        		content().length);
    	return fullHttpRequest;
	}
	
	private static List<String> usedHeaders=new ArrayList<String>(){
		
		private String _key;
		
		{
			add(HttpHeaderNames.HOST.toString());
			add(HttpHeaderNames.CONNECTION.toString());
			add(HttpHeaderNames.ACCEPT_ENCODING.toString());
			add(HttpHeaderNames.CONTENT_LENGTH.toString());
			_key=_key.substring(1);
		}

		public boolean add(String e) {
			boolean flag=super.add(e);
			if(flag){
				_key=","+_key+e;
			}
			return flag;
		};
		
		
		@Override
		public String toString() {
			return "["+_key+"]";
		}
		
	};
	
	
	private void checkHeader(){
		Map<String, Object> headers=request.getRequestMeta().getHeaders();
		for(String key:headers.keySet()){
			if(usedHeaders.contains(key)){
				throw new RuntimeException("invalid header name : ["+key+"], custome header mustnot be in "
			+usedHeaders.toString());
			}
		}
	}
	
	protected HttpMethod httpMethod(){
		return request.httpMethod;
	}
	
	protected URI uri(){
		return URI.create(request.requestMeta.getUrl());
	}
	
	protected Map<String, Object> head(){
		return request.requestMeta.getHeaders();
	}
	
	protected byte[] content(){
		return request.requestMeta.getContent();
	}
	
	protected boolean isKeepAlive(){
		return request.isKeepAlive();
	}
	
	protected String acceptEncoding(){
		return request.getAcceptEncoding();
	}
	

}
