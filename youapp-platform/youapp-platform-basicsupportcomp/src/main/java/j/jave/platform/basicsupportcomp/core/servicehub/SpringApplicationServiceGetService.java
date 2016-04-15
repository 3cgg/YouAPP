package j.jave.platform.basicsupportcomp.core.servicehub;

import j.jave.kernal.jave.service.JService;

public interface SpringApplicationServiceGetService extends JService{

	public JService getService(Class<?> clazz);
	
}
