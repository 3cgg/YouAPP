package test.j.jave.framework.temp.test.service;

import j.jave.framework.commons.eventdriven.servicehub.JAPPEvent;
import j.jave.framework.commons.eventdriven.servicehub.JListenerOnEvent;


@JListenerOnEvent(name=TestServicePrintListener.class)
public class TestServicePrintEvent extends JAPPEvent<TestServicePrintEvent> {

	public TestServicePrintEvent(Object source) {
		super(source);
	}

}
