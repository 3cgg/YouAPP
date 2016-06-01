package j.jave.platform.standalone.data;

import j.jave.kernal.jave.model.JModel;

public class ObjectWrapper implements JModel {

	private String key;
	
	private Object object;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
