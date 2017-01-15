package me.bunny.kernel.eventdriven.servicehub.monitor;

import java.util.ArrayList;
import java.util.List;

import me.bunny.kernel._c.model.JModel;

public class JServiceRuntimeMeta implements JModel {
	
	private String serviceName;
	
	private String serviceFacotoryName;
	
	private List<String> listenerNames=new ArrayList<String>();
	
	private String status;
	
	private String serviceClassLoader;

	private String serviceFactoryClassLoader;
	
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

	public String getServiceClassLoader() {
		return serviceClassLoader;
	}

	public void setServiceClassLoader(String serviceClassLoader) {
		this.serviceClassLoader = serviceClassLoader;
	}

	public String getServiceFactoryClassLoader() {
		return serviceFactoryClassLoader;
	}

	public void setServiceFactoryClassLoader(String serviceFactoryClassLoader) {
		this.serviceFactoryClassLoader = serviceFactoryClassLoader;
	}

}
