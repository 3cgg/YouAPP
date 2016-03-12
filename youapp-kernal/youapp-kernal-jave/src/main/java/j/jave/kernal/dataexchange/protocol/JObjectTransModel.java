package j.jave.kernal.dataexchange.protocol;

import j.jave.kernal.jave.model.JModel;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class JObjectTransModel implements JModel{

	private String url;
	
	// object data
	private List<JObjectWrapper>objectWrappers=new ArrayList<JObjectWrapper>();
	
	private JProtocol sendProtocol;
	
	private JProtocol expectedProtocol;
	
	public JProtocol getSendProtocol() {
		return sendProtocol;
	}

	public void setSendProtocol(JProtocol sendProtocol) {
		this.sendProtocol = sendProtocol;
	}

	public JProtocol getExpectedProtocol() {
		return expectedProtocol;
	}

	public void setExpectedProtocol(JProtocol expectedProtocol) {
		this.expectedProtocol = expectedProtocol;
	}
	
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
	
}
