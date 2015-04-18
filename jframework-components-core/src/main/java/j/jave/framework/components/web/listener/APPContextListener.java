package j.jave.framework.components.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APPContextListener implements ServletContextListener {
	private static final Logger LOGGER=LoggerFactory.getLogger(APPContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
