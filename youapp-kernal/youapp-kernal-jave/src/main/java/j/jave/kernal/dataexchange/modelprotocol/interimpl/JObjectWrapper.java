package j.jave.kernal.dataexchange.modelprotocol.interimpl;

import j.jave.kernal.jave.model.JModel;

@SuppressWarnings("serial")
public class JObjectWrapper implements JModel {

	private String fullClassName;
	
	private Object object;

	public String getFullClassName() {
		return fullClassName;
	}

	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	
}
