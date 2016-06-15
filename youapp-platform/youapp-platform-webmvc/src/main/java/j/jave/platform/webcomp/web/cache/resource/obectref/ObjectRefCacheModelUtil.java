package j.jave.platform.webcomp.web.cache.resource.obectref;

import j.jave.kernal.jave.support.resourceuri.ResourceCacheModel;
import j.jave.kernal.jave.utils.JStringUtils;

class ObjectRefCacheModelUtil {

	public static String key(ResourceCacheModel resourceCacheModel){
		return key(resourceCacheModel.getUri());
	}
	
	public static String key(String identifier){
		String key="";
		if(JStringUtils.isNotNullOrEmpty(identifier)){
			key=key+"-"+identifier;
		}
		return key;
	}
}
