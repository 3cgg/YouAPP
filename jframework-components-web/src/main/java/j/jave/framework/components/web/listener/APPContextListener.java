package j.jave.framework.components.web.listener;

import j.jave.framework.utils.JStringUtils;

import java.io.File;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * initial the context of the APP. the is the major context listener, <strong> Note that it's mandatory for the APP.</strong>  including 
 * <p>1. put all jars in the WEB-INF/lib in the class path of the property "java.class.path"
 * <p>2. put all classes in the WEB-INF/classes in the class path of the property "java.class.path"
 * <p>3. custom file directories or jar files (split by ";") that need be added into CLASSPATH ,see initial parameter : {@value #CUSTOM_CLASSPATH} 
 * @author J
 */
public class APPContextListener implements ServletContextListener {
	private static final Logger LOGGER=LoggerFactory.getLogger(APPContextListener.class);
	
	/**
	 * other file directory or jar file that need be added in to CLASSPATH.
	 */
	private static final String CUSTOM_CLASSPATH="j.jave.framework.CUSTOM_CLASSPATH";
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			Properties properties=System.getProperties();
			String javaClassPath=(String) properties.get("java.class.path");
			String webLib= sce.getServletContext().getRealPath("WEB-INF/lib");
			LOGGER.info("WEB LIB PATH : "+webLib );
			File libFile=new File(webLib);
			StringBuffer libStringBuffer=new StringBuffer();
			if(libFile.exists()){
				File[] files=libFile.listFiles();
				if(files!=null){
					for (int i = 0; i < files.length; i++) {
						File file=files[i];
						libStringBuffer.append(";"+(webLib+"/"+file.getName()));
					}
				}
			}
			LOGGER.info("Jars [WEB-INF/lib] added : "+libStringBuffer.toString());
			
			String customClassPath= sce.getServletContext().getInitParameter(CUSTOM_CLASSPATH);
			LOGGER.info("Custom CLASSPATH added : "+customClassPath);
			
			if(JStringUtils.isNullOrEmpty(customClassPath)){
				customClassPath="";
			}
			
			String classes= sce.getServletContext().getRealPath("WEB-INF/classes");
			LOGGER.info("Classes [WEB-INF/classes] added : "+classes);
			System.setProperty("java.class.path", javaClassPath+";"+libStringBuffer.toString()+";"+classes+";"+customClassPath);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
