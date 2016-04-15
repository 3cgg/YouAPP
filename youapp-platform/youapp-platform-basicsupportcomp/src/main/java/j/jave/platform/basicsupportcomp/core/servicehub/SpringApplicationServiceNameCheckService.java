package j.jave.platform.basicsupportcomp.core.servicehub;

import j.jave.kernal.jave.service.JService;

public interface SpringApplicationServiceNameCheckService extends JService{

	public boolean valid(Class<?> clazz);
	
}
