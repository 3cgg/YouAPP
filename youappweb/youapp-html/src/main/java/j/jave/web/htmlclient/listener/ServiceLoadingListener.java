package j.jave.web.htmlclient.listener;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServiceLoadingListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		JServiceFactoryManager.get().registerAllServices();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
