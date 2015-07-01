package j.jave.framework.components.test.service;

import j.jave.framework.commons.service.JService;

public interface TestLoginService extends JService {

	public void login(String name,String password);
	
}
