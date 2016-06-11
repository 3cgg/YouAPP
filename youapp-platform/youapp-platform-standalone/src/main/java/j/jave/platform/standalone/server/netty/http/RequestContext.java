package j.jave.platform.standalone.server.netty.http;


import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;
import j.jave.platform.standalone.model.AbstractContext;

public class RequestContext extends AbstractContext{

	private transient HttpRequest request;
	
	private transient LastHttpContent lastHttpContent  ;
	
	/**
	 * the unique identifier of request.
	 */
	private String requestUnique;
	
	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public LastHttpContent getLastHttpContent() {
		return lastHttpContent;
	}

	public void setLastHttpContent(LastHttpContent lastHttpContent) {
		this.lastHttpContent = lastHttpContent;
	}

	public String getRequestUnique() {
		return requestUnique;
	}

	public void setRequestUnique(String requestUnique) {
		this.requestUnique = requestUnique;
	}
	
}
