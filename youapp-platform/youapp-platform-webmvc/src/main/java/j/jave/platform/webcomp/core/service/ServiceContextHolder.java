package j.jave.platform.webcomp.core.service;

import j.jave.platform.webcomp.web.youappmvc.HttpContextHolder;

public final class ServiceContextHolder {
	
	public static final ServiceContext get(){
		return HttpContextHolder.get().getServiceContext();
	}
	
}
