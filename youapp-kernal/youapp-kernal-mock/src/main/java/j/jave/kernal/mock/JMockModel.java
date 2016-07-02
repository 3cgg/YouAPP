package j.jave.kernal.mock;

import j.jave.kernal.jave.model.JModel;

public class JMockModel implements JModel {

	/**
	 * the data
	 */
	private String data ;
	
	/**
	 * where the data locates, its higher than {@link #data()}
	 */
	private String uri;
	
	/**
	 * whether to mock the target or not
	 */
	boolean mock;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isMock() {
		return mock;
	}

	public void setMock(boolean mock) {
		this.mock = mock;
	}
	
}
