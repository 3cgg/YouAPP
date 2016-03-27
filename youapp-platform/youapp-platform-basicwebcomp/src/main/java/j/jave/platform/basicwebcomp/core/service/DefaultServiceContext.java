package j.jave.platform.basicwebcomp.core.service;

import org.springframework.beans.factory.InitializingBean;

public class DefaultServiceContext implements ServiceContextSupport ,InitializingBean{

	private static ServiceContext instance;
	
	private static ServiceContext getDefault(){
		if(instance==null){
			ServiceContext serviceContext=new ServiceContext();
			SessionUser user=new SessionUser();
			user.setUserId("default-user-id-as-no-user-session");
			user.setUserName("SYS");
			serviceContext.setSessionUser(user);
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
