package j.jave.framework.servicehub;

import j.jave.framework.context.JContext;
import j.jave.framework.exception.JInitializationException;
import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.io.JClassRootPathResolver;
import j.jave.framework.io.JFileResource;
import j.jave.framework.logging.JLoggerFactory;
import j.jave.framework.servicehub.exception.JUncheckedServiceException;
import j.jave.framework.support._package.JDefaultPackageScan;
import j.jave.framework.utils.JCollectionUtils;
import j.jave.framework.utils.JPropertiesUtils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * call the method {@link #registerAllServices()} to register all services when you need.
 * @author J
 *
 */
public final class JServiceFactoryManager{
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	// the collection contains those services that is services.properties.
	private List<Class<? extends JServiceFactorySupport<? extends JService>>> staticDefinedServiceFactories=new ArrayList<Class<? extends JServiceFactorySupport<? extends JService>>>();
	{
		try{
			final Map<String, String> items=new HashMap<String, String>();
			final ClassLoader classLoader= JServiceFactoryManager.class.getClassLoader();
			
			// first get all services from the root file (generally, also as classpath).
			URI path=new JClassRootPathResolver("services.properties").resolver();
			if(path!=null){
				JFileResource fileResource= new JFileResource(new File(path));
				if(fileResource.exists()){
					Properties properties= JPropertiesUtils.loadProperties(new JFileResource(new File(path)));
					JPropertiesUtils.each(properties, new JPropertiesUtils.Process() {
						@Override
						public void process(Object key, Object value,
								Properties properties) throws Exception {
							String serviceType=((String) key).trim();
							String serviceImpl=((String) value).trim();
							if(!items.containsKey(serviceType)){
								items.put(serviceType, serviceImpl);
								staticDefinedServiceFactories.add((Class<? extends JServiceFactorySupport<? extends JService>>) classLoader.loadClass(serviceImpl));
							}
						}
					});
				}
			}
			
			// second , get services from the definition in the framework
			URL url=JServiceFactoryManager.class.getClassLoader().getResource("services.properties");
			if(url!=null){
				if(url!=null){
					File file=new File(JServiceFactoryManager.class.getClassLoader().getResource("services.properties").toURI());
					if(file.exists()){
						Properties properties= JPropertiesUtils.loadProperties(new JFileResource(file));
						JPropertiesUtils.each(properties, new JPropertiesUtils.Process() {
							@Override
							public void process(Object key, Object value,
									Properties properties) throws Exception {
								String serviceType=((String) key).trim();
								String serviceImpl=((String) value).trim();
								if(!items.containsKey(serviceType)){
									items.put(serviceType, serviceImpl);
									staticDefinedServiceFactories.add((Class<? extends JServiceFactorySupport<? extends JService>>) classLoader.loadClass(serviceImpl));
								}
							}
						});
					}
				}
			}
			System.out.println("service manager processed items: "+items.size());
		}catch(Exception e){
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
	 * third,scan all service meta data from {@link JContext#getServiceMetas()}
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
				JDefaultPackageScan packageScan=new JDefaultPackageScan(JServiceFactorySupport.class);
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
				List<ServiceMeta> serviceMetas= JContext.get().getServiceMetaProvider().getServiceMetas();
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
			}catch(Exception e){
				throw new JUncheckedServiceException(e);
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
		JContext.get().getServiceMetaProvider().addServiceMeta(serviceMeta);
	}
	
	
	
	
	
}
