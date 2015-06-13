package j.jave.framework.temp.test.service;

import j.jave.framework.servicehub.JService;

public interface TestService  extends JService,TestServicePrintListener{

	void show();
}
