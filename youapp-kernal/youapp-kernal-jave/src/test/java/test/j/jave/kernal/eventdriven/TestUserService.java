package test.j.jave.kernal.eventdriven;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

public class TestUserService extends JServiceFactorySupport<TestUserService> implements JService , UserFindListener {

	public TestUserService() {
		super(TestUserService.class);
	}

	@Override
	public Object trigger(UserFindEvent event) {
		return "jia.zhong.jin".equalsIgnoreCase(event.getName())+"[jia.zhong.jin]";
	}

	@Override
	public TestUserService getService() {
		return new TestUserService();
	}
	
}
