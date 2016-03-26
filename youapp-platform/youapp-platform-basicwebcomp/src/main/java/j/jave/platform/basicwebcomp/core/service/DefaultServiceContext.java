package j.jave.platform.basicwebcomp.core.service;

import org.springframework.beans.factory.InitializingBean;

import j.jave.platform.basicwebcomp.login.model.User;

public class DefaultServiceContext implements ServiceContextSupport ,InitializingBean{

	private static ServiceContext instance;
	
	private static ServiceContext getDefault(){
		if(instance==null){
			ServiceContext serviceContext=new ServiceContext();
			User user=new User();
			user.setId("default-user-id-as-no-user-session");
			user.setUserName("SYS");
			serviceContext.setUser(user);
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
