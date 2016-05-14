package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JQueueElement;
import j.jave.kernal.eventdriven.servicehub.JQueueElementDistributer;
import j.jave.kernal.eventdriven.servicehub.JQueueElementDistributer.JQueueElementDistributerConfig;
import j.jave.kernal.eventdriven.servicehub.JQueueElementDistributer.JQueueElementHandler;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestEndNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestStartNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServicesRegisterEndNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServicesRegisterStartNotifyEvent;
import j.jave.kernal.jave.json.JJSON;
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
	
	private JQueueElementDistributer<TemporayObject> queueDistributeProcessor=null;
	{
		JQueueElementDistributerConfig queueElementDistributerConfig=new JQueueElementDistributerConfig();
		queueElementDistributerConfig.setName(JDefaultServiceMonitor.class.getSimpleName());
		queueElementDistributerConfig.setFixedThreadCount(1);
		JQueueElementHandler<TemporayObject> queueElementHandler=new JQueueElementHandler<TemporayObject>() {

			@Override
			public boolean isLaterProcess(TemporayObject execution) {
				return false;
			}

			@Override
			public Runnable taskProvided(TemporayObject execution) {
				execution.getTemporayCallback().call();
				return null;
			}

			@Override
			public void postProcess(TemporayObject execution) {
			}

			@Override
			public boolean isHandOff(TemporayObject execution) {
				return false;
			}
			
		};
		
		queueDistributeProcessor=new JQueueElementDistributer<TemporayObject>(queueElementHandler,queueElementDistributerConfig);
	
	}
	
	public static interface TemporayCallback{
		public void call();
	}
	
	
	private static class TemporayObject implements JQueueElement ,Comparable<TemporayObject>{
		private JAPPEvent<?> event;
		
		private TemporayCallback temporayCallback;
		
		public TemporayObject(JAPPEvent<?> event) {
			this.event = event;
		}
		
		public TemporayObject(JAPPEvent<?> event,TemporayCallback temporayCallback) {
			this.event = event;
			this.temporayCallback=temporayCallback;
		}
		
		public void setTemporayCallback(TemporayCallback temporayCallback) {
			this.temporayCallback = temporayCallback;
		}
		
		public TemporayCallback getTemporayCallback() {
			return temporayCallback;
		}
		
		@Override
		public int compareTo(TemporayObject o) {
			return event.getPriority()-o.event.getPriority();
		}
		
		@Override
		public String desc() {
			return JJSON.get().formatObject(event);
		}
		
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
		this.serviceHubReadyTime=event.getTime();
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
		this.servicesRegisterEndTime=event.getTime();
		return null;
	}

	@Override
	public Object trigger(JServicesRegisterStartNotifyEvent event) {
		this.servicesRegisterEndTime=event.getTime();
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
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("end event priority  : "+event.getPriority()+"  name : "+event.getClass().getName());
		}
		TemporayObject temporayObject=new TemporayObject(event);
		temporayObject.setTemporayCallback(new TemporayCallback() {
			@Override
			public void call() {
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("end event priority  : "+event.getPriority()+"  name : "+event.getClass().getName());
				}
				JEventProcessingStatus eventProcessingStatus=serviceMonitorStorage.getEventProcessingStatus(event.getEvent().getUnique());
				if(eventProcessingStatus==null){
					eventProcessingStatus=new JEventProcessingStatus();
				}
				eventProcessingStatus.setUnique(event.getEvent().getUnique());
				eventProcessingStatus.setEndTime(event.getTime());
				serviceMonitorStorage.store(eventProcessingStatus);
			}
		});
		queueDistributeProcessor.addExecution(temporayObject);
		return null;
	}
	
	@Override
	public Object trigger(JEventRequestStartNotifyEvent event) {
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug("start event priority  : "+event.getPriority()+"  name : "+event.getClass().getName());
		}
		TemporayObject temporayObject=new TemporayObject(event);
		temporayObject.setTemporayCallback(new TemporayCallback() {
			@Override
			public void call() {
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug("start event priority  : "+event.getPriority()+"  name : "+event.getClass().getName());
				}
				JEventProcessingStatus eventProcessingStatus=serviceMonitorStorage.getEventProcessingStatus(event.getEvent().getUnique());
				if(eventProcessingStatus==null){
					eventProcessingStatus=new JEventProcessingStatus();
				}
				eventProcessingStatus.setUnique(event.getEvent().getUnique());
				eventProcessingStatus.setStartTime(event.getTime());
				serviceMonitorStorage.store(eventProcessingStatus);
			}
		});
		queueDistributeProcessor.addExecution(temporayObject);
		return null;
	}
	
	@Override
	public JEventProcessingStatus getEventProcessingStatus(String eventId) {
		return serviceMonitorStorage.getEventProcessingStatus(eventId);
	}
	
	
	
	
}
