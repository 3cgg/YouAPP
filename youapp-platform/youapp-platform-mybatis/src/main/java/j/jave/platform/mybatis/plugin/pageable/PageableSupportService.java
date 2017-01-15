package j.jave.platform.mybatis.plugin.pageable;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel._c.service.JCacheService;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.support.JDefaultHashCacheService;
import me.bunny.kernel.eventdriven.JServiceOrder;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import org.springframework.stereotype.Service;

@Service(PageableSupportService.BEAN_NAME)
@JServiceOrder(value=999)
public class PageableSupportService extends SpringServiceFactorySupport<PageableSupportService>
implements JService{

	public static final String BEAN_NAME="PageableSupportService";
	
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
