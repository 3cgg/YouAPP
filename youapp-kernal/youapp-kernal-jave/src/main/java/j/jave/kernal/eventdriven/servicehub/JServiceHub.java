/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.JOrder;
import j.jave.kernal.eventdriven.JOrdered;
import j.jave.kernal.eventdriven.exception.JServiceRegisteringException;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceExistsEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceExistsListener;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceInstallEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceInstallListener;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceListenerDisableEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceListenerDisableListener;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceListenerEnableEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceListenerEnableListener;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceUninstallEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceUninstallListener;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServicesOnListenerEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServicesOnListenerListener;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceHubMeta;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceHubMonitorEvent;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceHubMonitorListener;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceMonitorEvent;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceMonitorListener;
import j.jave.kernal.eventdriven.servicehub.monitor.JServiceRuntimeMeta;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.reflect.JReflect;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JCollectionUtils.EntryCallback;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * service hub. auto-loading all the implementations {@link JServiceFactory}
 * @author J
 */
class JServiceHub implements JService  ,JServiceFactory<JServiceHub>,JServiceListenerDetectListener,
JServiceInstallListener,JServiceUninstallListener,JServiceListenerEnableListener,JServiceListenerDisableListener
,JServiceGetListener ,JServiceRegisterListener,JServiceMonitorListener,JServiceHubMonitorListener,JServiceExistsListener
,JServicesOnListenerListener{
	
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	/**
	 * KEY: service. implements <code>JService</code>
	 * <p>VALUE : service factory .implements <code>JServiceFactory</code>
	 */
	private static Map<Class<?>, JServiceFactory<?>> services=new ConcurrentHashMap<Class<?>, JServiceFactory<?>>();
	
	/**
	 * KEY: service. implements <code>JService</code>
	 * <p>VALUE: listener.  extends <code>JAPPListener</code>
	 */
	private final static Map<Class<?>, List<Class<?>>> serviceListeners=new ConcurrentHashMap<Class<?>, List<Class<?>>>();

	/**
	 * KEY: listener.  extends <code>JAPPListener</code>
	 * <p>VALUE: services-ordered entities.
	 */
	private final static Map<Class<?>, List<ServiceOrdered>> listenerServices=new ConcurrentHashMap<Class<?>, List<ServiceOrdered>>();

	
	private class ServiceOrdered implements Comparable<ServiceOrdered>{
		
		private Class<?> serviceClass;
		
		private Integer order;
		
		public ServiceOrdered(Class<?> serviceClass,int order) {
			this.serviceClass=serviceClass;
			this.order=order;
		}
		
		@Override
		public int compareTo(ServiceOrdered o) {
			return o.order.compareTo(this.order);
		}
		
	}
	
	@Override
	public boolean available() {
		return true;
	}
	
	/**
	 * find all listeners on the event, then trigger one by one
	 * @param event
	 * @return <code>EventExecutionResult</code> , all listener returned. at least an empty object array if no listener found.
	 */ 
	public EventExecutionResult executeEventOnListener(JAPPEvent<?> event){
		EventExecutionResult eventExecutionResult=new EventExecutionResult();
		try{
			Object[] objects=null;
			Class<?> eventClass=event.getClass();
			JListenerOnEvent eventListener= eventClass.getAnnotation(JListenerOnEvent.class);
			if(eventListener==null){
				throw new IllegalStateException(eventClass.getName()+"must be modified by annotaion @JEventListener");
			}
			Class<? extends JAPPListener>  runningListener=eventListener.name();
			List<ServiceOrdered> serviceClasses= listenerServices.get(runningListener);
			
			if(serviceClasses!=null&&!serviceClasses.isEmpty()){
				objects=new Object[serviceClasses.size()];
				boolean received=false;
				for(int i=0;i<serviceClasses.size();i++){
					ServiceOrdered serviceOrdered=serviceClasses.get(i);
					Class<?> serviceClass=serviceOrdered.serviceClass;
					if(serviceHubManager.isEnable(serviceClass, runningListener)){
						received=true;
						JAPPListener listener=null;
						if(JServiceInstallListener.class==runningListener){
							listener=(JAPPListener)services.get(serviceClass).getService();
						}
						else{
							listener=(JAPPListener) getService(serviceClass);
						}
						LOGGER.info("{ "+listener.getClass().getName()+" service } listenered on the event : "
								+event.getUnique()+"-|-"+event.getClass().getName());
						objects[i]=JReflect.invoke(listener, "trigger", new Class<?>[]{event.getClass()},new Object[]{event});
					}
				}
				if(!received){
					String info="no any service listeners on the event : "
							+event.getUnique()+"-|-"+event.getClass().getName();
					LOGGER.info(info);
					throw new IllegalStateException(info);
				}
			}
			eventExecutionResult.setObjects(objects);
		}catch(Exception e){
			LOGGER.error("Event Execute Error , caused by :",e);
			eventExecutionResult.setException(e);
		}
		return eventExecutionResult;
	}
	
	
	/**
	 * get service, if the service gets lost , thrown some exception.
	 * @param clazz
	 * @return
	 * @see JServiceHubManager
	 */
	@SuppressWarnings("unchecked")
	public  <T> T getService(Class<T> clazz){
		T service=null;
		JServiceFactory<?> serviceFactory=services.get(clazz);
		if(serviceFactory!=null){
			// check if the service is active, the service may be disable if it is uninstalled.
			if(serviceHubManager.isActive(clazz)){
				service= (T) serviceFactory.getService();
			}
			else if(serviceHubManager.isUninstall(clazz)){
				throw new JServiceStatusException("service ("+serviceFactory.getName()+") is uninstalled.");
			}
			else{
				throw new JServiceStatusException("service ("+serviceFactory.getName()+") is inactive.");
			}
		}
		else{
			throw new JServiceStatusException("service ("+clazz.getName()+") is missing in the system.");
		}
		return service;
	}
	
	public synchronized void register(Class<?> clazz,JServiceFactory<?> serviceFactory){
		if(services.containsKey(clazz)){
			JServiceFactory<?> exists=services.get(clazz);
			throw new JServiceRegisteringException(clazz.getName()+" is registered , name : "
					+ exists.getName()+" , service factory : "+exists.getClass().getName());
		}
		
		//check whether the service factory is available.
		if(!serviceFactory.available()){
			LOGGER.info("service factory ("+serviceFactory.getName()+") is unavaiable, drop it.");
			return ;
		}
		
		services.put(clazz, serviceFactory);
		serviceHubManager.addNewService(clazz);
		trigger(new JServiceListenerDetectEvent(this, JAPPEvent.HIGEST, clazz,serviceFactory.getClass()));
	}
	
	private JServiceHub(){}
	
	private static JServiceHub serviceHub;
	
	public static JServiceHub get(){
		if(serviceHub==null){
			serviceHub=new JServiceHub();
			//register self.
			serviceHub.register(JServiceHub.class, serviceHub);
		}
		return serviceHub;
	}

	@Override
	public Object trigger(JServiceListenerDetectEvent event) {
		Class<?> serviceClass =  event.getServiceClass();
		
		Class<?> serviceFactoryClass= event.getServiceFactoryClass();
		
		JOrder order =serviceFactoryClass.getAnnotation(JOrder.class);
		int orderValue=JOrdered.LOWEST_PRECEDENCE;
		if(order!=null){
			orderValue=order.value();
		}
		
		List<Class<?>> listenerClasses= JClassUtils.getAllInterfaces(serviceClass, JAPPListener.class);
		if(listenerClasses.size()>0){
			for(int i=0;i<listenerClasses.size();i++){
				Class<?> listenerClass=listenerClasses.get(i);
				JEventOnListener eventOnListener=listenerClass.getAnnotation(JEventOnListener.class);
				if(eventOnListener!=null){
					List<ServiceOrdered> lnservices= listenerServices.get(listenerClass);
					if(lnservices==null){
						lnservices=new ArrayList<ServiceOrdered>();
						listenerServices.put(listenerClass, lnservices);
					}
					ServiceOrdered serviceOrdered=new ServiceOrdered(serviceClass, orderValue);
					lnservices.add(serviceOrdered);
					Collections.sort(lnservices);
				}
			}
		}
		serviceListeners.put(serviceClass, listenerClasses);
		return new Object();
	}

	@Override
	public JServiceHub getService() {
		return get();
	}

	@Override
	public String getName() {
		return "Service Hub";
	}

	@Override
	public String getUniqueId() {
		return JUniqueUtils.unique();
	}

	@Override
	public String describer() {
		return "Service Hub , contains all services provided by the platform.";
	}
	
	@Override
	public Object trigger(JServiceInstallEvent event) {
		serviceHubManager.installService(event.getServiceClass());
		return true;
	}
	
	@Override
	public Object trigger(JServiceUninstallEvent event) {
		serviceHubManager.uninstallService(event.getServiceClass());
		return true;
	}
	
	@Override
	public Object trigger(JServiceListenerEnableEvent event) {
		serviceHubManager.enableServiceListener(event.getServiceClass(), event.getListener());
		return true;
	}
	@Override
	public Object trigger(JServiceListenerDisableEvent event) {
		serviceHubManager.disableServiceListener(event.getServiceClass(), event.getListener());
		return true;
	}
	
	@SuppressWarnings("serial")
	class JServiceStatusException extends RuntimeException {

		public JServiceStatusException(String message){
			super(message);
		}
		
		public JServiceStatusException(Exception e){
			super(e);
		}
	}

	
	private final JServiceHubManager serviceHubManager=new JServiceHubManager();
	
	class JServiceHubManager{
		
		Map<Class<?>, ServiceControlModel> serviceControlModels=new ConcurrentHashMap<Class<?>, ServiceControlModel>();

		class ServiceControlModel{
			boolean active=true;
			boolean install=true;
			List<Class<? extends JAPPListener>> disabled=new ArrayList<Class<? extends JAPPListener>>(6);
		}
		
		boolean addNewService(Class<?> service){
			ServiceControlModel serviceControlModel=new ServiceControlModel();
			serviceControlModels.put(service, serviceControlModel);
			return true;
		}
		
		boolean uninstallService(Class<?> service){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			serviceControlModel.install=false;
			serviceControlModel.active=false;
			serviceControlModel.disabled=new ArrayList<Class<? extends JAPPListener>>();
			return true;
		}
		
		boolean installService(Class<?> service){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			serviceControlModel.install=true;
			serviceControlModel.active=true;
			return true;
		}
		
		
		boolean disableServiceListener(Class<?> service,Class<? extends JAPPListener> listener){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			serviceControlModel.disabled.add(listener);
			return true;
		}
		
		boolean enableServiceListener(Class<?> service,Class<? extends JAPPListener> listener){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			serviceControlModel.disabled.remove(listener);
			return true;
		}
		
		boolean isActive(Class<?> service){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			return serviceControlModel.active;
		}

		boolean isInstall(Class<?> service){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			return serviceControlModel.install;
		}
		
		boolean isUninstall(Class<?> service){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			return !serviceControlModel.install;
		}
		
		boolean isEnable(Class<?> service,Class<? extends JAPPListener> listener){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			return !serviceControlModel.disabled.contains(listener);
		}
		
		boolean isDisable(Class<?> service,Class<? extends JAPPListener> listener){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			return serviceControlModel.disabled.contains(listener);
		}
		
	}
	
	
	@Override
	public JService trigger(JServiceGetEvent event) {
		return (JService) getService(event.getServiceName());
	}
	
	@Override
	public void trigger(JServiceRegisterEvent event) {
		register(event.getServiceName(), event.getServiceFactory());
	}

	private JServiceHubMeta serviceHubMeta;

	@Override
	public List<JServiceRuntimeMeta> trigger(final JServiceMonitorEvent event) {
		if(serviceHubMeta==null){
			refreshServiceHubMetaInfo();
		}
		final List<JServiceRuntimeMeta> serviceRuntimeMetas=new ArrayList<JServiceRuntimeMeta>(event.getServiceNames().size());
		final Map<String,JServiceRuntimeMeta> serviceRuntimeMetaMap= serviceHubMeta.getServiceRuntimeMetas();
		try{
			JCollectionUtils.each(event.getServiceNames(), new JCollectionUtils.CollectionCallback<String>(){
				@Override
				public void process(String value) throws Exception {
					if(serviceRuntimeMetaMap.containsKey(value)){
						serviceRuntimeMetas.add(serviceRuntimeMetaMap.get(value));
					}
				}
			});
		}catch(Exception e){
			throw new JEventExecutionException(e);
		}
		return serviceRuntimeMetas;
	}
	
	private synchronized void refreshServiceHubMetaInfo() {
		try{
			serviceHubMeta=new JServiceHubMeta();
			serviceHubMeta.setServiceCount(services.size());
			JCollectionUtils.each(services, new EntryCallback<Class<?>, JServiceFactory<?>>() {
				@Override
				public void process(Class<?> key, JServiceFactory<?> value)
						throws Exception {
					JServiceRuntimeMeta serviceRuntimeMeta=new JServiceRuntimeMeta();
					serviceRuntimeMeta.setServiceName(key.getName());
					serviceRuntimeMeta.setServiceFacotoryName(value.getName());
					if(serviceHubManager.isActive(key)){
						serviceHubMeta.setActiveServiceCount(serviceHubMeta.getActiveServiceCount()+1);
						serviceHubMeta.getActiveServiceNames().put(key.getName(), value.getName());
						serviceRuntimeMeta.setStatus("active");
					}
					else{
						serviceHubMeta.setInactiveServiceCount(serviceHubMeta.getInactiveServiceCount()+1);
						serviceHubMeta.getInactiveServiceNames().put(key.getName(), value.getName());
						serviceRuntimeMeta.setStatus("inactive");
					}
					serviceHubMeta.getServiceRuntimeMetas().put(key.getName(), serviceRuntimeMeta);
				}
			});
			
			JCollectionUtils.each(serviceListeners, new EntryCallback<Class<?>, List<Class<?>>>() {
				@Override
				public void process(Class<?> key, List<Class<?>> value)
						throws Exception {
					List<String> listenerNames=new ArrayList<String>();
					for(int i=0;i<value.size();i++){
						listenerNames.add(value.get(i).getName());
					}
					serviceHubMeta.getServicelistenernames().put(key.getName(), listenerNames);
					serviceHubMeta.getServiceRuntimeMetas().get(key.getName()).setListenerNames(listenerNames);
				}
			});
		}catch(Exception e){
			serviceHubMeta.setException(e);
		}
	}
	
	@Override
	public JServiceHubMeta trigger(JServiceHubMonitorEvent event) {
		if(event.isRefresh()){
			refreshServiceHubMetaInfo();
		}
		if(serviceHubMeta==null){
			refreshServiceHubMetaInfo();
		}
		return serviceHubMeta;
	}


	@Override
	public Object trigger(JServiceExistsEvent event) {
		Class<?> serviceClass=event.getServiceClass();
		return services.containsKey(serviceClass);
	}
	
	@Override
	public Object trigger(JServicesOnListenerEvent event) {
		Class<?> listenerClass= event.getListenerClass();
		return Collections.unmodifiableCollection(listenerServices.get(listenerClass));
	}
	
	
	
	
	
	
	
	
	
}
