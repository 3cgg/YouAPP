package j.jave.kernal.streaming.netty.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import j.jave.kernal.jave.exception.JNestedRuntimeException;
import j.jave.kernal.jave.utils.JStringUtils;

public abstract class ChannelRunnable {
	
	private final Request request;
	
	private final ChannelResponseCall responseCall;
	
	private ChannelFailedCall failedCall=ChannelFailedCall.NOTHING;
	
	private ChannelCancelledCall cancelledCall=ChannelCancelledCall.NOTHING;
	
	
	public ChannelRunnable(Request request,ChannelResponseCall responseCall) {
		this.request = request;
		this.responseCall=responseCall;
	}
	
	public final void cancelled(Object cause){
		try{
			cancelledCall.run(request, cause);
		}catch (Exception e) {
			e.printStackTrace();
			throw new JNestedRuntimeException(e);
		}
	}
	
	public final void fail(Throwable cause){
		try{
			failedCall.run(request, cause);
		}catch (Exception e) {
			e.printStackTrace();
			throw new JNestedRuntimeException(e);
		}
	}
	
	public final void response(Object object){
		try{
			responseCall.run(request, object);
		}catch (Exception e) {
			e.printStackTrace();
			throw new JNestedRuntimeException(e);
		}
	}
	
	public final ChannelFuture request(Channel channel){
		try{
			DefaultFullHttpRequest fullHttpRequest=prepare();
			ChannelFuture channelFuture= doRequest(channel,fullHttpRequest);
			return channelFuture;
		}catch (Exception e) {
			e.printStackTrace();
			throw new JNestedRuntimeException(e);
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
    	HttpHeaders httpHeaders=fullHttpRequest.headers();
    	
    	Map<String, Object> headers=request.getRequestMeta().getHeaders();
		for(Entry<String, Object> entry:headers.entrySet()){
			httpHeaders.set(entry.getKey(),entry.getValue());
		}
    	
    	httpHeaders.set(HttpHeaderNames.HOST, uri.getHost());
        //is keep-alive
        if(isKeepAlive()){
        	httpHeaders.set(HttpHeaderNames.CONNECTION, 
        			HttpHeaderValues.KEEP_ALIVE);
        }
        if(JStringUtils.isNotNullOrEmpty(acceptEncoding())){
        	httpHeaders.set(HttpHeaderNames.ACCEPT_ENCODING, 
            		HttpHeaderValues.GZIP);
        }
        httpHeaders.set(HttpHeaderNames.CONTENT_LENGTH, 
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
	
	public ChannelRunnable cancelledCall(ChannelCancelledCall cancelledCall) {
		this.cancelledCall = cancelledCall;
		return this;
	}
	
	public ChannelRunnable failedCall(ChannelFailedCall failedCall) {
		this.failedCall = failedCall;
		return this;
	}

}
