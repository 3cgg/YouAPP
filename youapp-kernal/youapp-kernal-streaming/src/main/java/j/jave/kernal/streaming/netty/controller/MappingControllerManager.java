package j.jave.kernal.streaming.netty.controller;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

class MappingControllerManager{
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(MappingControllerManager.class);

	private MappingControllerManager(){
	}
	
	private static final MappingControllerManager INSTANCE=new MappingControllerManager();
	
	public static MappingControllerManager get(){
		return INSTANCE;
	}
	
	private static final Map<String, MappingMeta> controllers=new ConcurrentHashMap<>();
	
	public boolean contains(String path){
		return controllers.containsKey(path);
	}
	
	public String getControllerName(String path){
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
	
	public void putMappingMeta(String path,MappingMeta mappingMeta){
		controllers.put(path, mappingMeta);
	}

	public Collection<String> urls(String unique){
		return Collections.unmodifiableCollection(controllers.keySet());
	}
	
	
	public boolean hasUrl(String url){
		return controllers.containsKey(url);
	}
	
}
