package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.dataexchange.impl.JEncoderRegisterService;
import j.jave.kernal.jave.model.JModel;

public class JMessage implements JModel{

	private String url;
	
	/**
	 * the BASE64 String
	 */
	private String data;
	
	/**
	 * the data byte encoder (JSON String bytes or Object bytes,etc. ) of {@link #data}
	 */
	private String dataByteEncoder;

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

	/**
	 * 
	 * @return the data byte encoder (JSON String bytes or Object bytes,etc. ) of {@link #data}
	 */
	public String getDataByteEncoder() {
		return dataByteEncoder;
	}

	/**
	 * @param dataByteEncoder the data byte encoder (JSON String bytes or Object bytes,etc. ) of {@link #data}
	 * @see JEncoderRegisterService
	 */
	public void setDataByteEncoder(String dataByteEncoder) {
		this.dataByteEncoder = dataByteEncoder;
	}

	

}
