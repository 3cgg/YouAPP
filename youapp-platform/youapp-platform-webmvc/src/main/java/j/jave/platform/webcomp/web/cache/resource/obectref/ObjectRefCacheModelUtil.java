package j.jave.platform.webcomp.web.cache.resource.obectref;

import me.bunny.kernel.jave.support.resourceuri.ResourceCacheModel;
import me.bunny.kernel.jave.utils.JStringUtils;

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
