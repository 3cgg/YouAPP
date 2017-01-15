package j.jave.platform.mybatis.multiv;

import org.apache.ibatis.io.Resources;
import org.springframework.stereotype.Service;

import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.app._c.sps.multiv.ComponentVersionPostService;
import me.bunny.app._c.sps.multiv.DynamicComponentVersionApplication;

@Service
public class DefaultComponentVersionPostService 
extends SpringServiceFactorySupport<ComponentVersionPostService> 
implements ComponentVersionPostService {

	@Override
	public void before(
			DynamicComponentVersionApplication dynamicComponentVersionApplication)
			throws Exception {
		Resources.setCustomeClassLoader(dynamicComponentVersionApplication.unique(), dynamicComponentVersionApplication.getUrlClassLoader());
	}

	@Override
	public void afterComplete(
			DynamicComponentVersionApplication dynamicComponentVersionApplication)
			throws Exception {

	}

	@Override
	public void cleanupAfterDestroy(
			DynamicComponentVersionApplication dynamicComponentVersionApplication)
			throws Exception {
		Resources.removeCustomClassLoader(dynamicComponentVersionApplication.unique());
	}
	
	
	
	
}
