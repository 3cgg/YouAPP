package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.jave.model.JModel;

import java.util.ArrayList;
import java.util.List;

public class JServiceRuntimeMeta implements JModel {
	
	private String serviceName;
	
	private String serviceFacotoryName;
	
	private List<String> listenerNames=new ArrayList<String>();
	
	private String status;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceFacotoryName() {
		return serviceFacotoryName;
	}

	public void setServiceFacotoryName(String serviceFacotoryName) {
		this.serviceFacotoryName = serviceFacotoryName;
	}

	public List<String> getListenerNames() {
		return listenerNames;
	}

	public void setListenerNames(List<String> listenerNames) {
		this.listenerNames = listenerNames;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
