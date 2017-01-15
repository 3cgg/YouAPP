package me.bunny.modular._p.streaming.netty.client;

import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMethod;

public abstract class Request {

	protected final HttpMethod httpMethod;
	
	protected RequestMeta requestMeta;
	
	protected boolean keepAlive=true;
	
	protected String acceptEncoding;

	public Request(HttpMethod httpMethod) {
		this.httpMethod=httpMethod;
	}
	
	public Request setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
		return this;
	}
	
	public boolean isKeepAlive() {
		return keepAlive;
	}

	/**
	 * {@link HttpHeaderValues#GZIP} /{@link HttpHeaderValues#X_GZIP} ...
	 * @param acceptEncoding
	 * @return
	 */
	public Request setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
		return this;
	}
	
	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}
	
	public RequestMeta getRequestMeta() {
		return requestMeta;
	}
	
	public static PostRequest post(RequestMeta requestMeta){
		PostRequest request=new PostRequest();
		request.requestMeta=requestMeta;
		return request;
	}
	
	public static GetRequest get(RequestMeta requestMeta){
		GetRequest request=new GetRequest();
		request.requestMeta=requestMeta;
		return request;
	}
	
	public static class PostRequest extends Request{

		public PostRequest() {
			super(HttpMethod.POST);
		}
		
		
		
	}
	
	public static class GetRequest extends Request{
		public GetRequest() {
			super(HttpMethod.GET);
		}
	}
	
	
	
	
}
