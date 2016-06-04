package j.jave.kernal.dataexchange.model;


public class DefaultMessageMeta implements MessageMeta {

	private String url;
	
	/**
	 * the base64 string.
	 */
	private String data;
	
	/**
	 * some encoder that encodes object the bytes of {@link #data()}
	 */
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

	/**
	 * 
	 * @return 
	 * @see DefaultMessageMeta#dataEncoder
	 */
	public String getDataEncoder() {
		return dataEncoder;
	}

	/**
	 * @param dataEncoder
	 * @see DefaultMessageMeta#dataEncoder
	 */
	public void setDataEncoder(String dataEncoder) {
		this.dataEncoder = dataEncoder;
	}
	
	
}
