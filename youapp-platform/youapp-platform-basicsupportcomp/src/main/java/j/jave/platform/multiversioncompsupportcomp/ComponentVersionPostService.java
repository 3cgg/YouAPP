package j.jave.platform.multiversioncompsupportcomp;

import j.jave.kernal.jave.service.JService;

public interface ComponentVersionPostService extends JService{

	void before(DynamicComponentVersionApplication dynamicComponentVersionApplication) throws Exception;
	
	void afterComplete(DynamicComponentVersionApplication dynamicComponentVersionApplication)  throws Exception;

	void cleanupAfterDestroy(DynamicComponentVersionApplication dynamicComponentVersionApplication) throws Exception;
}
