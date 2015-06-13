package j.jave.framework.temp.test.service;

import j.jave.framework.listener.JAPPListener;
import j.jave.framework.servicehub.JEventOnListener;


@JEventOnListener(name=TestServicePrintEvent.class)
public interface TestServicePrintListener extends JAPPListener{
	
	public Object trigger(TestServicePrintEvent event); 
	
}
