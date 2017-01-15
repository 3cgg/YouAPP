package me.bunny.app._c._web.core.service;

import me.bunny.app._c._web.web.youappmvc.HttpContextHolder;

public final class ServiceContextHolder {
	
	public static final ServiceContext get(){
		return HttpContextHolder.get().getServiceContext();
	}
	
}
