package j.jave.platform.mybatis.plugin.pageable;

import j.jave.kernal.eventdriven.JServiceOrder;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.service.JCacheService;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;

import org.springframework.stereotype.Service;

@Service(PageableSupportService.BEAN_NAME)
@JServiceOrder(value=999)
public class PageableSupportService extends SpringServiceFactorySupport<PageableSupportService>
implements JService{

	public static final String BEAN_NAME="j.jave.platform.mybatis.plugin.pageable.PageableSupportService";
	
	private JCacheService cacheService=JServiceHubDelegate.get().getService(this, JDefaultHashCacheService.class);
	
	public JCacheService getCacheService() {
		return cacheService;
	}
	
	public MappedMeta getMappedMeta(String mappedId){
		return (MappedMeta) getCacheService().get(mappedId);
	}
	
	public void putMappedMeta(MappedMeta mappedMeta){
		getCacheService().putNeverExpired(mappedMeta.getMappedId(), mappedMeta);
	}
	
	public boolean contains(String mappedId){
		return getCacheService().contains(mappedId);
	}
	
}
