package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.dataexchange.protocol.JProtocol;
import j.jave.kernal.jave.model.JModel;

public class ObjectTransModelMessage implements JModel{

	private String url;
	
	/**
	 * the BASE64 String
	 */
	private String data;
	
	private JProtocol protocol;

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

	public JProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(JProtocol protocol) {
		this.protocol = protocol;
	}

}
