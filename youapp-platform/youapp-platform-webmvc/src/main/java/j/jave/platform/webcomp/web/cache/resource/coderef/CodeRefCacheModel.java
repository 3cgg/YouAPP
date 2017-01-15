package j.jave.platform.webcomp.web.cache.resource.coderef;

import me.bunny.kernel._c.support.resourceuri.ResourceCacheModel;

public interface CodeRefCacheModel extends ResourceCacheModel {
	
	public String type();
	
	public String code();
	
	public String name();
	
}
