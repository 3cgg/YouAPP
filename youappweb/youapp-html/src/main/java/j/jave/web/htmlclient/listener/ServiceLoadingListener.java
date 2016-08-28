package j.jave.web.htmlclient.listener;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
