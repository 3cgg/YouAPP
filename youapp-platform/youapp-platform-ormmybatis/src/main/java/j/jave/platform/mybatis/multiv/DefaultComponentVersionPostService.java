package j.jave.platform.mybatis.multiv;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionPostService;
import j.jave.platform.multiversioncompsupportcomp.DynamicComponentVersionApplication;

import org.apache.ibatis.io.Resources;

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
