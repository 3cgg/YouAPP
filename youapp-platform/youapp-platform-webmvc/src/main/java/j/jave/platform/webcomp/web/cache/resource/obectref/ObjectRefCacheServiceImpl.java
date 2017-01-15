package j.jave.platform.webcomp.web.cache.resource.obectref;

import j.jave.platform.webcomp.web.cache.resource.ResourceCacheServiceSupport;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.support.resourceuri.IdentifierGenerator;
import me.bunny.kernel.jave.support.resourceuri.ResourceCacheModel;
import me.bunny.kernel.jave.support.resourceuri.SimpleStringIdentifierGenerator;

import org.springframework.stereotype.Service;

@Service(ObjectRefCacheServiceImpl.BEAN_NAME)
public class ObjectRefCacheServiceImpl extends ResourceCacheServiceSupport<ResourceCacheModel, Object>
		implements ObjectRefCacheService {
	
	public static final String BEAN_NAME="default-objectRefCacheServiceImpl";
	

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
