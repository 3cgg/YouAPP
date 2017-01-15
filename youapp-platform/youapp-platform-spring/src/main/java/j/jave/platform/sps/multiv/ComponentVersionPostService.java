package j.jave.platform.sps.multiv;

import me.bunny.kernel.jave.service.JService;

public interface ComponentVersionPostService extends JService{

	void before(DynamicComponentVersionApplication dynamicComponentVersionApplication) throws Exception;
	
	void afterComplete(DynamicComponentVersionApplication dynamicComponentVersionApplication)  throws Exception;

	void cleanupAfterDestroy(DynamicComponentVersionApplication dynamicComponentVersionApplication) throws Exception;
}
