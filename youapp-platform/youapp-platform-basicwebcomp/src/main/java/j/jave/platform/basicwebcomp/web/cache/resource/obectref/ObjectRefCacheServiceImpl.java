package j.jave.platform.basicwebcomp.web.cache.resource.obectref;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.support.resourceuri.IdentifierGenerator;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheModel;
import j.jave.kernal.jave.support.resourceuri.SimpleStringIdentifierGenerator;
import j.jave.platform.basicwebcomp.web.cache.resource.ResourceCacheServiceSupport;

import org.springframework.stereotype.Service;

@Service("j.jave.platform.basicwebcomp.web.cache.resource.obectref.ObjectRefCacheServiceImpl")
public class ObjectRefCacheServiceImpl extends ResourceCacheServiceSupport<ResourceCacheModel, Object>
		implements ObjectRefCacheService {

	private static SimpleStringIdentifierGenerator simpleStringIdentifierGenerator=new SimpleStringIdentifierGenerator(){
		public String namespace() {
			return "/objectref/";
		};
		
		@Override
		public String getKey(Object object) {
			return ObjectRefCacheModelUtil.key(super.getKey(object));
		}
	};
	
	@Override
	public IdentifierGenerator generator() {
		return simpleStringIdentifierGenerator;
	}

	@Override
	public void initResource(JConfiguration configuration) {
		
	}

}
