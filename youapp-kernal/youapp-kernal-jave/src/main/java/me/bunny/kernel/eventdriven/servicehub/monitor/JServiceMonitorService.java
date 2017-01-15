package me.bunny.kernel.eventdriven.servicehub.monitor;

import java.util.List;

import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.servicehub.listener.JServiceHubInitializedListener;
import me.bunny.kernel.eventdriven.servicehub.notify.JEventRequestEndNotifyListener;
import me.bunny.kernel.eventdriven.servicehub.notify.JEventRequestStartNotifyListener;
import me.bunny.kernel.eventdriven.servicehub.notify.JServiceAddNotifyListener;
import me.bunny.kernel.eventdriven.servicehub.notify.JServicesRegisterEndNotifyListener;
import me.bunny.kernel.eventdriven.servicehub.notify.JServicesRegisterStartNotifyListener;

public interface JServiceMonitorService extends JService
, JServiceAddNotifyListener, JServiceHubInitializedListener
,JServicesRegisterStartNotifyListener,JServicesRegisterEndNotifyListener
,JEventRequestStartNotifyListener,JEventRequestEndNotifyListener{

	/**
	 * inspect the service hub.
	 * @return
	 */
	JServiceHubMeta getServiceHubMeta();

	/**
	 * get service runtime metas of some services registered in the hub.
	 * @param serviceClasses
	 * @return
	 */
	List<JServiceRuntimeMeta> getServiceRuntimeMetas(Class<? extends JService>... serviceClasses);

	/**
	 * get service runtime meta of certain service registered in the hub.
	 * @param serviceClass
	 * @return
	 */
	JServiceRuntimeMeta getServiceRuntimeMeta(Class<? extends JService> serviceClass);

	/**
	 * inspect the time that all services registering cost.
	 * @return
	 */
	long getServicesRegisterTime();
	
	/**
	 * inspect the time that the service hub startup cost.
	 * @return
	 */
	long getServiceHubInitializeTime();
	
	/**
	 * get event processing information.
	 * @param eventId
	 * @return
	 */
	JEventProcessingStatus getEventProcessingStatus(String eventId);
	
}