package j.jave.platform.standalone.client.netty.http;


import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import j.jave.platform.standalone.model.AbstractContext;

public class ResponseContext extends AbstractContext{

	private transient HttpResponse response;
	
	private transient LastHttpContent lastHttpContent;

	/**
	 * the unique identifier of response.
	 */
	private String reponseUnique;
	
	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public LastHttpContent getLastHttpContent() {
		return lastHttpContent;
	}

	public void setLastHttpContent(LastHttpContent lastHttpContent) {
		this.lastHttpContent = lastHttpContent;
	}

	public String getReponseUnique() {
		return reponseUnique;
	}

	public void setReponseUnique(String reponseUnique) {
		this.reponseUnique = reponseUnique;
	}
	
}
