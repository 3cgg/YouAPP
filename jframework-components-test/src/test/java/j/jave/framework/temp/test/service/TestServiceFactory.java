package j.jave.framework.temp.test.service;

import j.jave.framework.servicehub.JAbstractServiceFactory;

public class TestServiceFactory extends JAbstractServiceFactory<TestService> {

	private TestService testService=new TestServiceImpl();;
	
	@Override
	public TestService getService() {
		return testService;
	}

}
