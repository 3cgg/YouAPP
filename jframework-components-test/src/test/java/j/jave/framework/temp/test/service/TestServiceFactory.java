package j.jave.framework.temp.test.service;

import j.jave.framework.servicehub.JServiceFactorySupport;

public class TestServiceFactory extends JServiceFactorySupport<TestService> {

	public TestServiceFactory(Class<TestService> registClass) {
		super(registClass);
	}
	
	public TestServiceFactory() {
		this(TestService.class);
	}

	private TestService testService=new TestServiceImpl();
	
	@Override
	public TestService getService() {
		return testService;
	}

}
