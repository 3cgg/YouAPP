package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestEndNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestStartNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServicesRegisterEndNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServicesRegisterStartNotifyEvent;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * collect all runtime meta data of the service or hub, it's functionality of tracking the status of the hub.
 * @author JIAZJ
 *
 */
public class JDefaultServiceMonitor extends JServiceFactorySupport<JServiceMonitorService>
implements JServiceMonitorService{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JDefaultServiceMonitor.class); 
	
	private JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	private static JServiceMonitorStorage serviceMonitorStorage;
	
	static{
		String serviceMonitorStorageClassName=JConfiguration.get().getString(JProperties.SERVICE_HUB_MONITOR_STATUS_STORAGE,
				JDefaultServiceMonitorStorage.class.getName());
		serviceMonitorStorage=(JServiceMonitorStorage) JClassUtils.newObject(JClassUtils.load(serviceMonitorStorageClassName));
	}
	
	@Override
	public Object trigger(JServiceAddNotifyEvent event) {
		LOGGER.info(event.getServiceClass().getName()+" added into hub.");
		return true;
	}
	
	@Override
	public JServiceMonitorService getService() {
		return this;
	}
	
	@Override
	public JServiceHubMeta getServiceHubMeta(){
		return serviceHubDelegate.addImmediateEvent(new JServiceHubMonitorEvent(this),JServiceHubMeta.class);
	}
	 
	@Override
	@SuppressWarnings("unchecked")
	public List<JServiceRuntimeMeta> getServiceRuntimeMetas(Class<? extends JService>... serviceClasses){
		List<JServiceRuntimeMeta> serviceRuntimeMetas=
				serviceHubDelegate.addImmediateEvent(new JServiceMonitorEvent(this,serviceClasses),List.class);
		return serviceRuntimeMetas==null?Collections.EMPTY_LIST:serviceRuntimeMetas;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JServiceRuntimeMeta getServiceRuntimeMeta(Class<? extends JService> serviceClass){
		List<JServiceRuntimeMeta> serviceRuntimeMetas=
				serviceHubDelegate.addImmediateEvent(new JServiceMonitorEvent(this,serviceClass),List.class);
		if(JCollectionUtils.hasInCollect(serviceRuntimeMetas)){
			return serviceRuntimeMetas.get(0);
		}
		return null;
	}

	@Override
	public Object trigger(JServiceHubInitializedEvent event) {
		LOGGER.info("the standalone service hub is initialized.");
		this.serviceHubReadyTime=new Date();
		return null;
	}
	
	/**
	 * it's the starting time to registering all services.
	 */
	private Date servicesRegisterStartTime;
	
	/**
	 * it's the end time to registering all services.
	 */
	private Date servicesRegisterEndTime;
	
	
	/**
	 * it's the ready time for service hub starting services.
	 */
	private Date serviceHubReadyTime;
	
	@Override
	public Object trigger(JServicesRegisterEndNotifyEvent event) {
		this.servicesRegisterEndTime=new Date();
		return null;
	}

	@Override
	public Object trigger(JServicesRegisterStartNotifyEvent event) {
		this.servicesRegisterEndTime=new Date();
		return null;
	}
	
	@Override
	public long getServicesRegisterTime() {
		return this.servicesRegisterEndTime.getTime()-this.servicesRegisterStartTime.getTime();
	}
	
	@Override
	public long getServiceHubInitializeTime() {
		return this.serviceHubReadyTime.getTime()-this.servicesRegisterStartTime.getTime();
	}
	
	@Override
	public Object trigger(JEventRequestEndNotifyEvent event) {
		JEventProcessingStatus eventProcessingStatus=serviceMonitorStorage.getEventProcessingStatus(event.getEvent().getUnique());
		eventProcessingStatus.setEndTime(new Date());
		serviceMonitorStorage.store(eventProcessingStatus);
		return null;
	}

	@Override
	public Object trigger(JEventRequestStartNotifyEvent event) {
		JEventProcessingStatus eventProcessingStatus=new JEventProcessingStatus();
		eventProcessingStatus.setUnique(event.getEvent().getUnique());
		eventProcessingStatus.setStartTime(new Date());
		serviceMonitorStorage.store(eventProcessingStatus);
		return null;
	}
	
	@Override
	public JEventProcessingStatus getEventProcessingStatus(String eventId) {
		return serviceMonitorStorage.getEventProcessingStatus(eventId);
	}
	
	
	
}
