package j.jave.platform.basicwebcomp.web.youappmvc.controller;

import j.jave.kernal.JConfiguration;
import j.jave.platform.basicwebcomp.web.util.MappingMeta;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MappingController{

	private static Map<String, MappingMeta> CONTROLLERS=new ConcurrentHashMap<>();

	public static final Map<String, Object> CONTROLLER_OBJECTS=new ConcurrentHashMap<String, Object>();

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

	public Object getControllerObjectByPath(String path){
		return CONTROLLER_OBJECTS.get(path);
	}

	public static void putMappingMeta(String path,MappingMeta mappingMeta){
		CONTROLLERS.put(path, mappingMeta);
	}

	public static void putControllerObject(String path,Object controllerObject){
		CONTROLLER_OBJECTS.put(path, controllerObject);
	}
	
	public static Set<String> urls(){
		return CONTROLLERS.keySet();
	}
	
	
	
	
	
	
	
	
	
	
}
