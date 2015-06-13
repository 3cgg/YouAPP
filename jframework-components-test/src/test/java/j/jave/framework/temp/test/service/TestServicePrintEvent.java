package j.jave.framework.temp.test.service;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.servicehub.JListenerOnEvent;


@JListenerOnEvent(name=TestServicePrintListener.class)
public class TestServicePrintEvent extends JAPPEvent<TestServicePrintEvent> {

	public TestServicePrintEvent(Object source) {
		super(source);
	}

}
