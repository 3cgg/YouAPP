package j.jave.platform.standalone.interimpl;

import j.jave.platform.standalone.data.MessageMeta;

public class DefaultMessageMeta implements MessageMeta {

	private String url;
	
	private String data;
	
	private String dataEncoder;
	
	@Override
	public String url() {
		return url;
	}

	@Override
	public String data() {
		return data;
	}
	
	@Override
	public String dataEncoder() {
		return dataEncoder;
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

	public String getDataEncoder() {
		return dataEncoder;
	}

	public void setDataEncoder(String dataEncoder) {
		this.dataEncoder = dataEncoder;
	}
	
	
}
