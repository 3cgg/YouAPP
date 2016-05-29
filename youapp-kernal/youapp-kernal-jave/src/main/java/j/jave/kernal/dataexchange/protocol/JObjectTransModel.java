package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.jave.model.JModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class JObjectTransModel implements JModel{

	private String url;
	
	private JProtocol protocol;
	
	// object data
	private List<JObjectWrapper>objectWrappers=new ArrayList<JObjectWrapper>();
	
	public synchronized void setObjectWrapper(JObjectWrapper objectWrapper) {
		objectWrappers.add(objectWrapper);
	}

	public List<JObjectWrapper> getObjectWrappers() {
		return objectWrappers;
	}

	public void setObjectWrappers(List<JObjectWrapper> objectWrappers) {
		this.objectWrappers = objectWrappers;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JProtocol getProtocol() {
		return protocol;
	}

	public void setProtocol(JProtocol protocol) {
		this.protocol = protocol;
	}
	
}
