package j.jave.kernal.jave.service;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JStringUtils;

public abstract class JXMLConfigProvider implements JServiceInterfaceProvider {
	
	public final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Class<T> getServiceInterface()  {
		String interfaceClassStr=JConfiguration.get().getString(key());
		Class<T> clazz=null;
		if(JStringUtils.isNullOrEmpty(interfaceClassStr)){
			LOGGER.info(desc()+", cannot find any config ["+key()+"].");
			clazz=(Class<T>) defaultInterface();
			LOGGER.info(desc()+"use default service interface: "+clazz.getName());
			return clazz;
		}
		try{
			clazz=JClassUtils.load(interfaceClassStr,classLoader());
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			clazz=(Class<T>) defaultInterface();
			LOGGER.info(desc()+"use default service interface: "+clazz.getName());
		}
		return clazz;
	}
	
	protected abstract String key();
	
	protected abstract  Class<? extends JService> defaultInterface();
	
	protected String desc(){
		return this.getClass().getSimpleName();
	}
	
	/**
	 * override the method to use different class loader.
	 * @return
	 */
	protected ClassLoader classLoader(){
		return Thread.currentThread().getContextClassLoader();
	}
}
