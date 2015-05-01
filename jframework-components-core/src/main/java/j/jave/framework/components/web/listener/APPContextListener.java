package j.jave.framework.components.web.listener;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APPContextListener implements ServletContextListener {
	private static final Logger LOGGER=LoggerFactory.getLogger(APPContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			Properties properties=System.getProperties();
			String javaClassPath=(String) properties.get("java.class.path");
			String webLib= sce.getServletContext().getRealPath("WEB-INF/lib");
			LOGGER.info("WEB LIB PATH : "+webLib );
			File libFile=new File(webLib);
			StringBuffer stringBuffer=new StringBuffer();
			if(libFile.exists()){
				File[] files=libFile.listFiles();
				if(files!=null){
					for (int i = 0; i < files.length; i++) {
						File file=files[i];
						stringBuffer.append(";"+(webLib+"/"+file.getName()));
					}
				}
			}
			LOGGER.info("JARS ADDED : "+stringBuffer.toString());
			System.setProperty("java.class.path", javaClassPath+";"+stringBuffer.toString());
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
