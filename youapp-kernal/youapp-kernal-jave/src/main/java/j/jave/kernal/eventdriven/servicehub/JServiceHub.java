/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.JOrdered;
import j.jave.kernal.eventdriven.JServiceOrder;
import j.jave.kernal.eventdriven.JServiceOrders;
import j.jave.kernal.eventdriven.exception.JServiceRegisteringException;
import j.jave.kernal.eventdriven.servicehub.aop.JServiceMethodInterceptor;
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
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyEvent;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.reflect.JReflectionUtils;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JCollectionUtils.EntryCallback;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
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

	public static List<JServiceFactory<?>> interceptorFactories=null;
	
	private boolean factoryInstallCompleted=false;
	
	void setFactoryInstallCompleted(boolean factoryInstallCompleted) {
		this.factoryInstallCompleted = factoryInstallCompleted;
	}
	
	boolean isFactoryInstallCompleted() {
		return factoryInstallCompleted;
	}
	
	public static class ServiceOrdered implements Comparable<ServiceOrdered>{
		
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
	public EventExecutionResult executeEventOnListener(JYouAPPEvent<?> event){
		EventExecutionResult eventExecutionResult=new EventExecutionResult();
		try{
			Object[] objects=null;
			Class<?> eventClass=event.getClass();
			JListenerOnEvent eventListener= eventClass.getAnnotation(JListenerOnEvent.class);
			if(eventListener==null){
				throw new IllegalStateException(eventClass.getName()+" must be modified by annotaion @JEventListener");
			}
			Class<? extends JYouAPPListener>  runningListener=eventListener.name();
			List<ServiceOrdered> serviceClasses= listenerServices.get(runningListener);
			
			if(serviceClasses!=null&&!serviceClasses.isEmpty()){
				objects=new Object[serviceClasses.size()];
				boolean received=false;
				for(int i=0;i<serviceClasses.size();i++){
					ServiceOrdered serviceOrdered=serviceClasses.get(i);
					Class<?> serviceClass=serviceOrdered.serviceClass;
					if(serviceHubManager.isEnable(serviceClass, runningListener)){
						received=true;
						JYouAPPListener listener=null;
						if(JServiceInstallListener.class==runningListener){
							listener=(JYouAPPListener)services.get(serviceClass).getService();
						}
						else{
							listener=(JYouAPPListener) getService(serviceClass);
						}
						LOGGER.info("{ "+listener.getClass().getName()+" service } listenered on the event : "
								+event.getUnique()+"-|-"+event.getClass().getName());
						objects[i]=JReflectionUtils.invoke(listener, "trigger", new Class<?>[]{event.getClass()},new Object[]{event});
					}
				}
				if(!received){
					String info="no any service listeners on the event : "
							+event.getUnique()+"-|-"+event.getClass().getName();
					LOGGER.info(info);
					throw new IllegalStateException(info);
				}
			}
			else{
				throw new IllegalStateException("cannot find any one service for the listerner:"+runningListener.getName());
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
		if(JServiceHub.class!=clazz){
			JAssert.isTrue(factoryInstallCompleted, "factories are not installed completed.");
		}
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
	
	/**
	 * register the service, true return if registering successfully, otherwise false.
	 * the method may throw any unexpected exception, such as that the service hub
	 * may contain the registering service.
	 * @param clazz
	 * @param serviceFactory
	 * @return true if registering successfully, otherwise false.
	 * @see JServiceRegisteringException
	 */
	public synchronized boolean register(Class<?> clazz,JServiceFactory<?> serviceFactory){
		if(services.containsKey(clazz)){
			JServiceFactory<?> exists=services.get(clazz);
			throw new JServiceRegisteringException(clazz.getName()+" is registered , name : "
					+ exists.getName()+" , service factory : "+exists.getClass().getName());
		}
		
		//check whether the service factory is available.
		if(!serviceFactory.available()){
			LOGGER.info("service factory ("+serviceFactory.getName()+") is unavaiable, drop it.");
			return false;
		}
		
		services.put(clazz, serviceFactory);
		serviceHubManager.addNewService(clazz);
		trigger(new JServiceListenerDetectEvent(this, JYouAPPEvent.HIGEST, clazz,serviceFactory.getClass()));
		return true;
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
		Map<Class<?>, Integer> serviceOrderOnListeners=new HashMap<Class<?>, Integer>();
		JServiceOrders serviceOrdersAnnotation =serviceFactoryClass.getAnnotation(JServiceOrders.class);
		if(serviceOrdersAnnotation!=null){
			JServiceOrder[] serviceOrders=  serviceOrdersAnnotation.serviceOrders();
			if(serviceOrders.length>0){
				for(JServiceOrder serviceOrder:serviceOrders){
					Class<?>[]  listenerClasses=serviceOrder.listenerClasses();
					if(listenerClasses.length>0){
						for(Class<?> listenerClass:listenerClasses){
							serviceOrderOnListeners.put(listenerClass, serviceOrder.value());
						}
					}
				}
			}
		}
		
		JServiceOrder serviceOrderAnnotation =serviceFactoryClass.getAnnotation(JServiceOrder.class);
		if(serviceOrderAnnotation!=null){
			Class<?>[]  listenerClasses=serviceOrderAnnotation.listenerClasses();
			if(listenerClasses.length>0){
				for(Class<?> listenerClass:listenerClasses){
					serviceOrderOnListeners.put(listenerClass, serviceOrderAnnotation.value());
				}
			}
		}
		
		List<Class<?>> listenerClasses= JClassUtils.getAllInterfaces(serviceClass, JYouAPPListener.class);
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
					Integer orderValue=serviceOrderOnListeners.get(listenerClass);
					if(orderValue==null){
						orderValue=JOrdered.LOWEST_PRECEDENCE;
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
			List<Class<? extends JYouAPPListener>> disabled=new ArrayList<Class<? extends JYouAPPListener>>(6);
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
			serviceControlModel.disabled=new ArrayList<Class<? extends JYouAPPListener>>();
			return true;
		}
		
		boolean installService(Class<?> service){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			serviceControlModel.install=true;
			serviceControlModel.active=true;
			return true;
		}
		
		
		boolean disableServiceListener(Class<?> service,Class<? extends JYouAPPListener> listener){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			serviceControlModel.disabled.add(listener);
			return true;
		}
		
		boolean enableServiceListener(Class<?> service,Class<? extends JYouAPPListener> listener){
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
		
		boolean isEnable(Class<?> service,Class<? extends JYouAPPListener> listener){
			ServiceControlModel serviceControlModel= serviceControlModels.get(service);
			return !serviceControlModel.disabled.contains(listener);
		}
		
		boolean isDisable(Class<?> service,Class<? extends JYouAPPListener> listener){
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
		boolean successRegister=register(event.getServiceName(), event.getServiceFactory());
		if(successRegister&&isFactoryInstallCompleted()){
			JServiceAddNotifyEvent serviceAddNotifyEvent=new JServiceAddNotifyEvent(this, event.getServiceName());
			JServiceHubDelegate.get().addDelayEvent(serviceAddNotifyEvent);
		}
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
		return Collections.unmodifiableList(serviceRuntimeMetas);
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
					serviceRuntimeMeta.setServiceClassLoader(key.getClassLoader().getClass().getName());
					serviceRuntimeMeta.setServiceFacotoryName(value.getName());
					serviceRuntimeMeta.setServiceFactoryClassLoader(value.getClass().getClassLoader().getClass().getName());
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
		return Collections.unmodifiableList(listenerServices.get(listenerClass));
	}
	
	private final Object syncInterceptorFactories=new Object();
	
	Collection<JServiceFactory<?>> getInterceptors(){
		if(interceptorFactories==null){
			synchronized (syncInterceptorFactories) {
				if(interceptorFactories==null){
					interceptorFactories=new ArrayList<JServiceFactory<?>>();
					for(JServiceFactory<?> factory:services.values()){
						if(JServiceMethodInterceptor.class.isInstance(factory)){
							interceptorFactories.add(factory);
						}
					}
				}
			}
		}
		return Collections.unmodifiableCollection(interceptorFactories);
		
	}

	@Override
	public Class<JServiceHub> getServiceClass() {
		return JServiceHub.class;
	}

	@Override
	public Class<?> getServiceImplClass() {
		return JServiceHub.class;
	}
	
	
	
	
	
	
}
