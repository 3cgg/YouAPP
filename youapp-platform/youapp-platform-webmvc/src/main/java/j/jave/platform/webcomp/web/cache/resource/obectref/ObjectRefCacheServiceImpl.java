package j.jave.platform.webcomp.web.cache.resource.obectref;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.support.resourceuri.IdentifierGenerator;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheModel;
import j.jave.kernal.jave.support.resourceuri.SimpleStringIdentifierGenerator;
import j.jave.platform.webcomp.web.cache.resource.ResourceCacheServiceSupport;

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
