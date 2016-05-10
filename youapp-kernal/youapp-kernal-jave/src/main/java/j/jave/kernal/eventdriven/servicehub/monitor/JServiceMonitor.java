package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyListener;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;

public class JServiceMonitor extends JServiceFactorySupport<JServiceMonitor>
implements JService, JServiceAddNotifyListener{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JServiceMonitor.class); 
	
	@Override
	public Object trigger(JServiceAddNotifyEvent event) {
		LOGGER.info("scanning ::::: "+event.getServiceClass().getName());
		return true;
	}
	
	@Override
	public JServiceMonitor getService() {
		return this;
	}
	
}
