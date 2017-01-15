package me.bunny.app._c.sps.multiv;

import me.bunny.kernel._c.service.JService;

public interface ComponentVersionPostService extends JService{

	void before(DynamicComponentVersionApplication dynamicComponentVersionApplication) throws Exception;
	
	void afterComplete(DynamicComponentVersionApplication dynamicComponentVersionApplication)  throws Exception;

	void cleanupAfterDestroy(DynamicComponentVersionApplication dynamicComponentVersionApplication) throws Exception;
}
