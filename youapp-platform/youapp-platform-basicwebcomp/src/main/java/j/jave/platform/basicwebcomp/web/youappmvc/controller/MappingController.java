package j.jave.platform.basicwebcomp.web.youappmvc.controller;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.web.util.MappingMeta;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class MappingController{
	private static final JLogger LOGGER=JLoggerFactory.getLogger(MappingController.class);
	
	private final Map<String, MappingMeta> CONTROLLERS=new ConcurrentHashMap<>();

	private static Map<String, MappingController> multiVerMappingControllers=new ConcurrentHashMap<String, MappingController>();
	
	/*
	public MappingController(JConfiguration configuration){
		MappingDetector mappingDetector=new MappingDetector(configuration);
		mappingDetector.detect();
		List<MappingMeta> mappingMetas= mappingDetector.getMappingMetas();
		for(MappingMeta meta:mappingMetas){
			CONTROLLERS.put(meta.getPath(), meta);
		}
	}
	*/

	public MappingController(JConfiguration configuration){

	}

	public boolean contains(String path){
		return CONTROLLERS.containsKey(path);
	}
	
	public String getBeanName(String path){
		MappingMeta mappingMeta= CONTROLLERS.get(path);
		return mappingMeta.getControllerName();
	}
	
	public String getMethodName(String path){
		MappingMeta mappingMeta= CONTROLLERS.get(path);
		mappingMeta.getMethodName();
		return mappingMeta.getControllerName();
	}
	
	public MappingMeta getMappingMeta(String path){
		MappingMeta mappingMeta= CONTROLLERS.get(path);
		return mappingMeta;
	}

	private static ReentrantLock lock=new ReentrantLock();
	
	public static void putMappingMeta(String path,MappingMeta mappingMeta,String unique){
		try{
			lock.lockInterruptibly();
			MappingController mappingController= multiVerMappingControllers.get(unique);
			if(mappingController==null){
				mappingController=new MappingController(JConfiguration.get());
				multiVerMappingControllers.put(unique, mappingController);
			}
			mappingController.CONTROLLERS.put(path, mappingMeta);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}finally{
			lock.unlock();
		}
	}

	public static final String PLATFORM="YOUAPP:COM:0";
	
	public static void putMappingMeta(String path,MappingMeta mappingMeta){
		putMappingMeta(path, mappingMeta, PLATFORM);
	}

	public static Set<String> urls(String unique){
		return multiVerMappingControllers.get(unique).CONTROLLERS.keySet();
	}
	
	
	public static boolean hasUrl(String url,String unique){
		if(JStringUtils.isNullOrEmpty(unique)){
			for(MappingController controller:multiVerMappingControllers.values()){
				if(controller.contains(url)){
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
	
	public static MappingController getMappingController(String unique){
		if(unique==null){
			unique=PLATFORM;
		}
		return multiVerMappingControllers.get(unique);
	}
	
}
