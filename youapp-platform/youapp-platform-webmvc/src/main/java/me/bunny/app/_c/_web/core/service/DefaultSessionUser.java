package me.bunny.app._c._web.core.service;

import org.springframework.beans.factory.InitializingBean;

public class DefaultSessionUser implements SessionUserSupport ,InitializingBean{

	private static SessionUser instance;
	
	private static SessionUser getDefault(){
		if(instance==null){
			SessionUserImpl user=new SessionUserImpl();
			user.setUserId("def-user-id-as-no-user-session");
			user.setUserName("SYS");
			instance=user;
		}
		return instance;
	}
	
	@Override
	public SessionUser getSessionUser() {
		return getDefault();
	}
	
	public static SessionUser getDefaultSessionUser(){
		return getDefault();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		getDefault();
	}
}
