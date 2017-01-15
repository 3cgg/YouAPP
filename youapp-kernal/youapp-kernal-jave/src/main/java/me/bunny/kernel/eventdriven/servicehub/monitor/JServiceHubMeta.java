package me.bunny.kernel.eventdriven.servicehub.monitor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.bunny.kernel._c.model.JModel;

public class JServiceHubMeta implements JModel {

	private Exception exception;
	
	private int serviceCount;
	
	private int activeServiceCount;
	
	private int inactiveServiceCount;
	
	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE : service factory name.implements <code>JServiceFactory</code>
	 */
	private Map<String, String> serviceNames=new ConcurrentHashMap<String,String>();
	
	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE : service factory name.implements <code>JServiceFactory</code>
	 */
	private Map<String, String> activeServiceNames=new ConcurrentHashMap<String,String>();
	
	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE : service factory name.implements <code>JServiceFactory</code>
	 */
	private Map<String, String> inactiveServiceNames=new ConcurrentHashMap<String,String>();
	
	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE: listener name.  extends <code>JAPPListener</code>
	 */
	private final  Map<String, List<String>> serviceListenerNames=new ConcurrentHashMap<String, List<String>>();

	private Map<String,JServiceRuntimeMeta> serviceRuntimeMetas=new ConcurrentHashMap<String,JServiceRuntimeMeta>();
	
	public int getServiceCount() {
		return serviceCount;
	}

	public void setServiceCount(int serviceCount) {
		this.serviceCount = serviceCount;
	}

	public int getActiveServiceCount() {
		return activeServiceCount;
	}

	public void setActiveServiceCount(int activeServiceCount) {
		this.activeServiceCount = activeServiceCount;
	}

	public int getInactiveServiceCount() {
		return inactiveServiceCount;
	}

	public void setInactiveServiceCount(int inactiveServiceCount) {
		this.inactiveServiceCount = inactiveServiceCount;
	}

	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE : service factory name.implements <code>JServiceFactory</code>
	 */
	public  Map<String, String> getServiceNames() {
		return serviceNames;
	}

	public  void setServiceNames(Map<String, String> serviceNames) {
		this.serviceNames = serviceNames;
	}
	
	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE: listener name.  extends <code>JAPPListener</code>
	 */
	public  Map<String, List<String>> getServicelistenernames() {
		return serviceListenerNames;
	}

	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE : service factory name.implements <code>JServiceFactory</code>
	 */
	public Map<String, String> getActiveServiceNames() {
		return activeServiceNames;
	}

	public void setActiveServiceNames(Map<String, String> activeServiceNames) {
		this.activeServiceNames = activeServiceNames;
	}

	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE : service factory name.implements <code>JServiceFactory</code>
	 */
	public  Map<String, String> getInactiveServiceNames() {
		return inactiveServiceNames;
	}

	public  void setInactiveServiceNames(
			Map<String, String> inactiveServiceNames) {
		this.inactiveServiceNames = inactiveServiceNames;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	/**
	 * KEY: service name. implements <code>JService</code>
	 * <p>VALUE: listener name.  extends <code>JAPPListener</code>
	 */
	public Map<String, List<String>> getServiceListenerNames() {
		return serviceListenerNames;
	}
	
	public Map<String, JServiceRuntimeMeta> getServiceRuntimeMetas() {
		return serviceRuntimeMetas;
	}
	
	public void setServiceRuntimeMetas(
			Map<String, JServiceRuntimeMeta> serviceRuntimeMetas) {
		this.serviceRuntimeMetas = serviceRuntimeMetas;
	}
	
}
