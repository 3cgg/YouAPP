package me.bunny.kernel.jave.service;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.utils.JStringUtils;

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
