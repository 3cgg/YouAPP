package me.bunny.app._c._web.core.service;

import org.springframework.beans.factory.InitializingBean;

public class DefaultServiceContext implements ServiceContextSupport ,InitializingBean{

	private static ServiceContext instance;
	
	private static ServiceContext getDefault(){
		if(instance==null){
			ServiceContext serviceContext=new ServiceContext();
			SessionUserImpl sessionUserImpl=(SessionUserImpl) DefaultSessionUser.getDefaultSessionUser();
			serviceContext.setTicket(sessionUserImpl.getTicket());
			serviceContext.setUserId(sessionUserImpl.getUserId());
			serviceContext.setUserName(sessionUserImpl.getUserName());
			instance=serviceContext;
		}
		return instance;
	}
	
	@Override
	public ServiceContext getServiceContext() {
		return getDefault();
	}
	
	public static ServiceContext getDefaultServiceContext(){
		return getDefault();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		getDefault();
	}
}
