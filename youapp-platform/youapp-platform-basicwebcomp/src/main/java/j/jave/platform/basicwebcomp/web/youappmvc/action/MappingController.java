package j.jave.platform.basicwebcomp.web.youappmvc.action;

import j.jave.kernal.JConfiguration;
import j.jave.platform.basicwebcomp.web.util.MappingDetector;
import j.jave.platform.basicwebcomp.web.util.MappingMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MappingController{
	
	
	private Map<String, MappingMeta> pathMetas=new HashMap<String, MappingMeta>();
	
	public MappingController(JConfiguration configuration){
		MappingDetector mappingDetector=new MappingDetector(configuration);
		mappingDetector.detect();
		List<MappingMeta> mappingMetas= mappingDetector.getMappingMetas();
		for(MappingMeta meta:mappingMetas){
			pathMetas.put(meta.getPath(), meta);
		}
	}
	
	public boolean contains(String path){
		return pathMetas.containsKey(path);
	}
	
	public String getBeanName(String path){
		MappingMeta mappingMeta= pathMetas.get(path);
		return mappingMeta.getControllerName();
	}
	
	public String getMethodName(String path){
		MappingMeta mappingMeta= pathMetas.get(path);
		mappingMeta.getMethodName();
		return mappingMeta.getControllerName();
	}
	
	public MappingMeta getMappingMeta(String path){
		MappingMeta mappingMeta= pathMetas.get(path);
		return mappingMeta;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
