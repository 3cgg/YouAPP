package j.jave.kernal.container.rhttp;

import j.jave.kernal.http.JHttpType;
import j.jave.kernal.jave.model.JModel;

public class RemoteURIInfo implements JModel {
	
	private String path;
	
	private JHttpType httpType;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public JHttpType getHttpType() {
		return httpType;
	}

	public void setHttpType(JHttpType httpType) {
		this.httpType = httpType;
	}
	
}
