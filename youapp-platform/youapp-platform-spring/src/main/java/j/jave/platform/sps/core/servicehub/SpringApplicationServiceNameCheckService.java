package j.jave.platform.sps.core.servicehub;

import me.bunny.kernel.jave.service.JService;

public interface SpringApplicationServiceNameCheckService extends JService{

	public boolean valid(Class<?> clazz);
	
}
