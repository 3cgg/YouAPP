package j.jave.platform.sps.core.servicehub;

import me.bunny.kernel._c.service.JService;

public interface SpringApplicationServiceNameCheckService extends JService{

	public boolean valid(Class<?> clazz);
	
}
