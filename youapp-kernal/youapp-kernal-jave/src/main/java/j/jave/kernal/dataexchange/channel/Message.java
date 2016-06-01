package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.jave.model.JModel;

public class Message implements JModel{

	private String url;
	
	/**
	 * the BASE64 String
	 */
	private String data;
	
	private String handler;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * the BASE64 String
	 * @return
	 */
	public String getData() {
		return data;
	}

	/**
	 * the BASE64 String
	 * @param data
	 */
	public void setData(String data) {
		this.data = data;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

}
