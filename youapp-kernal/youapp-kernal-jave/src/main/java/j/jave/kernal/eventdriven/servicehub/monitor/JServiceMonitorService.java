package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.jave.service.JService;

import java.util.List;

public interface JServiceMonitorService extends JService {

	/**
	 * get all existing services in the hub.
	 * @return
	 */
	public abstract JServiceHubMeta getServiceHubMeta();

	public abstract List<JServiceRuntimeMeta> getServiceRuntimeMetas(Class<? extends JService>... serviceClasses);

	public abstract JServiceRuntimeMeta getServiceRuntimeMeta(Class<? extends JService> serviceClass);

}