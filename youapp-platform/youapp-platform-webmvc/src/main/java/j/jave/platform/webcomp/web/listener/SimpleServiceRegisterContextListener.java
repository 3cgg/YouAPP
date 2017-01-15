package j.jave.platform.webcomp.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;

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
