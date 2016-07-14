package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.listener.JServiceHubInitializedListener;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestEndNotifyListener;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestStartNotifyListener;
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyListener;
import j.jave.kernal.eventdriven.servicehub.notify.JServicesRegisterEndNotifyListener;
import j.jave.kernal.eventdriven.servicehub.notify.JServicesRegisterStartNotifyListener;
import j.jave.kernal.jave.service.JService;

import java.util.List;

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