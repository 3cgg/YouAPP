/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub;

import j.jave.framework.commons.eventdriven.servicehub.eventlistener.JServiceInstallEvent;
import j.jave.framework.commons.eventdriven.servicehub.eventlistener.JServiceInstallListener;
import j.jave.framework.commons.eventdriven.servicehub.eventlistener.JServiceListenerDisableEvent;
import j.jave.framework.commons.eventdriven.servicehub.eventlistener.JServiceListenerDisableListener;
import j.jave.framework.commons.eventdriven.servicehub.eventlistener.JServiceListenerEnableEvent;
import j.jave.framework.commons.eventdriven.servicehub.eventlistener.JServiceListenerEnableListener;
import j.jave.framework.commons.eventdriven.servicehub.eventlistener.JServiceUninstallEvent;
import j.jave.framework.commons.eventdriven.servicehub.eventlistener.JServiceUninstallListener;
import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.reflect.JClassUtils;
import j.jave.framework.commons.reflect.JReflect;
import j.jave.framework.commons.service.JService;
import j.jave.framework.commons.utils.JUniqueUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * service hub. auto-loading all the implementations {@link JServiceFactory}
 * @author J
 */
class JServiceHub implements JService  ,JServiceFactory<JServiceHub>,JServiceListenerDetectListener,
JServiceInstallListener,JServiceUninstallListener,JServiceListenerEnableListener,JServiceListenerDisableListener
,JServiceGetListener ,JServiceRegisterListener{
	
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	/**
	 * KEY: service. implements <code>JService</code>
	 * <p>VALUE : service factory .implements <code>JServiceFactory</code>
	 */
	private static Map<Class<?>, JServiceFactory<?>> services=new ConcurrentHashMap<Class<?>, JServiceFactory<?>>();
	
	private Object sync=new Object();
	
	/**
	 * KEY: service. implements <code>JService</code>
	 * <p>VALUE: listener.  extends <code>JAPPListener</code>
	 */
	private final static Map<Class<?>, List<Class<?>>> serviceListeners=new ConcurrentHashMap<Class<?>, List<Class<?>>>();

	/**
	 * KEY: listener.  extends <code>JAPPListener</code>
	 * <p>VALUE: services. implements <code>JService</code>
	 */
	private final static Map<Class<?>, List<Class<?>>> listenerServices=new ConcurrentHashMap<Class<?>, List<Class<?>>>();

	
	/**
	 * find all listeners on the event, then trigger one by one
	 * @param event
	 * @return <code>Object[]</code> , all listener returned. at least an empty object array if no listener found.
	 */ 
	public Object[] executeEventOnListener(JAPPEvent<?> event){
		Object[] objects=new Object[]{};
		try{
			Class<?> eventClass=event.getClass();
			JListenerOnEvent eventListener= eventClass.getAnnotation(JListenerOnEvent.class);
			if(eventListener==null){
				throw new IllegalStateException(eventClass.getName()+"must be modified by annotaion @JEventListener");
			}
			Class<? extends JAPPListener>  runningListener=eventListener.name();
			List<Class<?>> serviceClasses= listenerServices.get(runningListener);
			
			if(serviceClasses!=null&&!serviceClasses.isEmpty()){
				objects=new Object[serviceClasses.size()];
				boolean received=false;
				for(int i=0;i<serviceClasses.size();i++){
					Class<?> serviceClass=serviceClasses.get(i);
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
					LOGGER.info("no any service listeners on the event : "
							+event.getUnique()+"-|-"+event.getClass().getName());
				}
			}
		}catch(Exception e){
			LOGGER.error("Event Execute Error , caused by :",e);
		}
		return objects;
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
				throw new JServiceStatusException("service is uninstalled.");
			}
			else{
				throw new JServiceStatusException("service is inactive.");
			}
		}
		else{
			throw new JServiceStatusException("service is missing in the system.");
		}
		return service;
	}
	
	public void register(Class<?> clazz,JServiceFactory<?> serviceFactory){
		synchronized (sync) {
			services.put(clazz, serviceFactory);
			serviceHubManager.addNewService(clazz);
			trigger(new JServiceListenerDetectEvent(this, JAPPEvent.HIGEST, clazz));
		}
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
		List<Class<?>> listenerClasses= JClassUtils.getAllInterfaces(serviceClass, JAPPListener.class);
		if(listenerClasses.size()>0){
			for(int i=0;i<listenerClasses.size();i++){
				Class<?> listenerClass=listenerClasses.get(i);
				JEventOnListener eventOnListener=listenerClass.getAnnotation(JEventOnListener.class);
				if(eventOnListener!=null){
					List<Class<?>> lnservices= listenerServices.get(listenerClass);
					if(lnservices==null){
						lnservices=new ArrayList<Class<?>>();
						listenerServices.put(listenerClass, lnservices);
					}
					lnservices.add(serviceClass);
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

	
	private JServiceHubManager serviceHubManager=new JServiceHubManager();
	
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
	
}
