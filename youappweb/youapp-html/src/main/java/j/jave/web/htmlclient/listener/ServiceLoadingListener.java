package j.jave.web.htmlclient.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;

public class ServiceLoadingListener implements ServletContextListener {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(ServiceLoadingListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			LOGGER.info("beign initializing sevice hub.");
			JServiceFactoryManager.get().registerAllServices();
			LOGGER.info("initialize sevice hub completely.");
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
