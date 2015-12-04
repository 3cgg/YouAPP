package test.j.jave.framework.temp.test.service;

import j.jave.framework.commons.eventdriven.servicehub.JAPPListener;
import j.jave.framework.commons.eventdriven.servicehub.JEventOnListener;


@JEventOnListener(name=TestServicePrintEvent.class)
public interface TestServicePrintListener extends JAPPListener{
	
	public Object trigger(TestServicePrintEvent event); 
	
}
