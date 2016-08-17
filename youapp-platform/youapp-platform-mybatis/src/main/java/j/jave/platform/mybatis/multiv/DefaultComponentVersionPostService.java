package j.jave.platform.mybatis.multiv;

import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.sps.multiv.ComponentVersionPostService;
import j.jave.platform.sps.multiv.DynamicComponentVersionApplication;

import org.apache.ibatis.io.Resources;
import org.springframework.stereotype.Service;

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
