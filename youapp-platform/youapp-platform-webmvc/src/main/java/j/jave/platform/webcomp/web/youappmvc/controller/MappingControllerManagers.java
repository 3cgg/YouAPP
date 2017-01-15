package j.jave.platform.webcomp.web.youappmvc.controller;

import me.bunny.app._c.data.web.mapping.MappingMeta;
import me.bunny.app._c.sps.core.context.SpringContextSupport;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.exception.JInitializationException;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

class MappingControllerManagers{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(MappingControllerManagers.class);
	
	private final static Map<String, MappingControllerManager> multiVerMappingControllers=new ConcurrentHashMap<String, MappingControllerManager>();
	
	public static class MappingControllerManager{
		
		/*
		public MappingControllerManager(JConfiguration configuration){
			MappingDetector mappingDetector=new MappingDetector(configuration);
			mappingDetector.detect();
			List<MappingMeta> mappingMetas= mappingDetector.getMappingMetas();
			for(MappingMeta meta:mappingMetas){
				CONTROLLERS.put(meta.getPath(), meta);
			}
		}
		*/

		public MappingControllerManager(JConfiguration configuration){

		}
		
		private final Map<String, MappingMeta> controllers=new ConcurrentHashMap<>();
		
		public boolean contains(String path){
			return controllers.containsKey(path);
		}
		
		public String getBeanName(String path){
			MappingMeta mappingMeta= controllers.get(path);
			return mappingMeta.getControllerName();
		}
		
		public String getMethodName(String path){
			MappingMeta mappingMeta= controllers.get(path);
			mappingMeta.getMethodName();
			return mappingMeta.getControllerName();
		}
		
		public MappingMeta getMappingMeta(String path){
			MappingMeta mappingMeta= controllers.get(path);
			return mappingMeta;
		}
		
		void put(String path, MappingMeta mappingMeta){
			controllers.put(path, mappingMeta);
		}
		
		public Collection<String> urls(){
			return Collections.unmodifiableCollection(controllers.keySet());
		}
	}
	

	private static ReentrantLock lock=new ReentrantLock();
	
	public static void putMappingMeta(String path,MappingMeta mappingMeta,String unique){
		try{
			lock.lockInterruptibly();
			MappingControllerManager mappingControllerManager= multiVerMappingControllers.get(unique);
			if(mappingControllerManager==null){
				mappingControllerManager=new MappingControllerManager(JConfiguration.get());
				multiVerMappingControllers.put(unique, mappingControllerManager);
			}
			mappingControllerManager.put(path, mappingMeta);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}finally{
			lock.unlock();
		}
	}

	public static final String PLATFORM=SpringContextSupport.PLATFORM;
	
	public static void putMappingMeta(String path,MappingMeta mappingMeta){
		putMappingMeta(path, mappingMeta, PLATFORM);
	}

	public static Collection<String> urls(String unique){
		return multiVerMappingControllers.get(unique).urls();
	}
	
	
	public static boolean hasUrl(String url,String unique){
		if(JStringUtils.isNullOrEmpty(unique)){
			for(MappingControllerManager controllerManager:multiVerMappingControllers.values()){
				if(controllerManager.contains(url)){
					return true;
				}
			}
			return false;
		}
		else{
			return multiVerMappingControllers.get(unique).contains(url);
		}
	}
	
	public static boolean hasUrl(String url){
		return hasUrl(url,null);
	}
	
	static MappingControllerManager getMappingControllerManager(String unique){
		if(unique==null){
			unique=PLATFORM;
		}
		return multiVerMappingControllers.get(unique);
	}
	
}
