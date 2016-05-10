package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.eventdriven.context.JEventDrivenContext;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedEvent;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.support._package.JDefaultClassesScanner;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * call the method {@link #registerAllServices()} to register all services when you need.
 * @author J
 *
 */
public final class JServiceFactoryManager{
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JServiceFactoryManager.class);
	
	// the collection contains services that are in youapp-*.xml.
	private List<Class<? extends JServiceFactorySupport<? extends JService>>> staticDefinedServiceFactories=new ArrayList<Class<? extends JServiceFactorySupport<? extends JService>>>();
	{
		try{
			final Map<String, String> items=new HashMap<String, String>();
			final ClassLoader classLoader= JServiceFactoryManager.class.getClassLoader();
			String services=JConfiguration.get().getString(JProperties.SERVICES, "");
			String[] serviceStrings=services.split(";");
			for(int i=0;i<serviceStrings.length;i++){
				String serviceString=serviceStrings[i];
				String[] ones=serviceString.split("=");
				if(ones.length==2){
					String serviceType=((String) ones[0]).trim();
					String serviceImpl=((String) ones[1]).trim();
					if(!items.containsKey(serviceType)){
						items.put(serviceType, serviceImpl);
						Class<? extends JServiceFactorySupport<? extends JService>> clazz=JClassUtils.load(serviceImpl, classLoader);
						staticDefinedServiceFactories.add(clazz);
					}
				}
			}
			LOGGER.info("service manager processed items: "+items.size());
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}
	}
	
	public static interface JServiceMetaProvider {

		public abstract void addServiceMeta(ServiceMeta serviceMeta);

		public abstract List<ServiceMeta> getServiceMetas();

	}
	
	public static class ServiceMeta{
		
		private Class<? extends JServiceFactorySupport<? extends JService>> serviceFactory;
		
		private String message;

		public Class<? extends JServiceFactorySupport<? extends JService>> getServiceFactory() {
			return serviceFactory;
		}

		public void setServiceFactory(
				Class<? extends JServiceFactorySupport<? extends JService>> serviceFactory) {
			this.serviceFactory = serviceFactory;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
	
	private List<Class<?  extends JServiceFactorySupport<? extends JService>>>  registers=new ArrayList<Class<?  extends JServiceFactorySupport<? extends JService>>>();
	
	boolean isScanRegistered=false;
	
	private JServiceFactoryManager(){
	}
	
	private static JServiceFactoryManager serviceFactoryManager;
	
	public static JServiceFactoryManager get(){
		if(serviceFactoryManager==null){
			synchronized (JServiceFactoryManager.class) {
				if(serviceFactoryManager==null){
					serviceFactoryManager=new JServiceFactoryManager();
				}
			}
		}
		return serviceFactoryManager;
	}
	
	/**
	 * register services according the below order 
	 * <pre>
	 * first , scan from static resource (services.properties)
	 * second, scan all classes in the classpath
	 * third,scan all service meta data from {@link JEventDrivenContext#getServiceMetas()}
	 * </pre>
	 */
	public synchronized void registerAllServices(){
		if(!isScanRegistered){
			isScanRegistered=true;
			try{
				
				//register services from static resource.
				for(int i=0;i<staticDefinedServiceFactories.size();i++){
					Class<? extends JServiceFactorySupport<? extends JService>> clazz=staticDefinedServiceFactories.get(i);
					if(!registers.contains(clazz)){
						JServiceFactoryRegister serviceFactoryRegister=(JServiceFactoryRegister) clazz.newInstance();
						serviceFactoryRegister.postRegister();
						registers.add(clazz);
					}
				}
				
				// default scan all package if any possible.
				JDefaultClassesScanner packageScan=new JDefaultClassesScanner(JServiceFactorySupport.class);
				Set<Class<?>> classes=  packageScan.scan();
				if(JCollectionUtils.hasInCollect(classes)){
					for (Iterator<Class<?>> iterator = classes.iterator(); iterator.hasNext();) {
						Class<? extends JServiceFactorySupport<? extends JService>> clazz =(Class<? extends JServiceFactorySupport<? extends JService>>) iterator.next();
						if(!registers.contains(clazz)){
							JServiceFactoryRegister serviceFactoryRegister=(JServiceFactoryRegister) clazz.newInstance();
							serviceFactoryRegister.postRegister();
							registers.add(clazz);
						}
					}
				}
				
				//scan context of JContext#getServiceMetas()
				List<ServiceMeta> serviceMetas= JEventDrivenContext.get().getServiceMetaProvider().getServiceMetas();
				if(JCollectionUtils.hasInCollect(serviceMetas)){
					for(int i=0;i<serviceMetas.size();i++){
						ServiceMeta serviceMeta=serviceMetas.get(i);
						Class<JServiceFactorySupport<? extends JService>> clazz =  (Class<JServiceFactorySupport<? extends JService>>) serviceMeta.getServiceFactory();
						if(!registers.contains(clazz)){
							JServiceFactoryRegister serviceFactoryRegister=(JServiceFactoryRegister) clazz.newInstance();
							serviceFactoryRegister.postRegister();
							registers.add(clazz);
						}
					}
				}
				// post process after service hub startup.
				JServiceHubDelegate.get().addImmediateEvent(new JServiceHubInitializedEvent(this, JConfiguration.get()));
			}catch(Exception e){
				throw new JInitializationException(e);
			}
		}
	}
	
	/**
	 * add more services factories that can be registered in service hub later. 
	 * generally,call the method during the application setup, and 
	 * before the method {@link #registerAllServices()}
	 * @param serviceMeta
	 */
	public  void initServiceMeta(ServiceMeta serviceMeta){
		JEventDrivenContext.get().getServiceMetaProvider().addServiceMeta(serviceMeta);
	}
	
	/**
	 * dynamic register service.
	 * @param clazz
	 */
	public synchronized void registerService(Class<? extends JServiceFactorySupport<JService>> clazz){
		try{
			if(!registers.contains(clazz)){
				JServiceFactoryRegister serviceFactoryRegister=(JServiceFactoryRegister) clazz.newInstance();
				serviceFactoryRegister.postRegister();
				registers.add(clazz);
			}
		}catch(Exception e){
			throw new JInitializationException(e);
		}
	}
	
	
	
	
}
