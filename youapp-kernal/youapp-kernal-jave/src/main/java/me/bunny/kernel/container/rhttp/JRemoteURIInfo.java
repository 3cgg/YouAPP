package me.bunny.kernel.container.rhttp;

import me.bunny.kernel._c.model.JModel;
import me.bunny.kernel.http.JHttpType;

public class JRemoteURIInfo implements JModel {
	
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
