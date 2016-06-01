package j.jave.platform.standalone.interimpl;

import j.jave.platform.standalone.data.MessageMeta;

public class DefaultMessageMeta implements MessageMeta {

	private String url;
	
	private String data;
	
	@Override
	public String url() {
		return url;
	}

	@Override
	public String data() {
		return data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
