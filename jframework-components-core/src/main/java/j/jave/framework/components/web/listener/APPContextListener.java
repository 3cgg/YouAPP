package j.jave.framework.components.web.listener;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * initial the context of the APP. the is the major context listener, <strong> Note that it's mandatory for the APP.</strong>  including 
 * <p>1. put all jars in the WEB-INF/lib in the class path of the property "java.class.path"
 * <p>1. put all classes in the WEB-INF/classes in the class path of the property "java.class.path"
 * @author J
 */
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
			LOGGER.info("Jars [WEB-INF/lib] added : "+stringBuffer.toString());
			String classes= sce.getServletContext().getRealPath("WEB-INF/classes");
			LOGGER.info("Classes [WEB-INF/classes] added : "+stringBuffer.toString());
			System.setProperty("java.class.path", javaClassPath+";"+stringBuffer.toString()+";"+classes);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
