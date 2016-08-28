package j.jave.platform.webcomp.web.listener;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class SimpleServiceRegisterContextListener implements ServletContextListener {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(SimpleServiceRegisterContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		LOGGER.info("beign initializing sevice hub.");
		JServiceFactoryManager.get().registerAllServices();
		LOGGER.info("initialize sevice hub completely.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	
}
