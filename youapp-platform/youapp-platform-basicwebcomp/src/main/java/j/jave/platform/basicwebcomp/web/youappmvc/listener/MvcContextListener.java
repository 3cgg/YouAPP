package j.jave.platform.basicwebcomp.web.youappmvc.listener;

import j.jave.kernal.jave.reflect.JClassPathList;
import j.jave.kernal.jave.utils.JClassPathUtils;

import java.io.File;

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
public class MvcContextListener implements ServletContextListener {
	private static final Logger LOGGER=LoggerFactory.getLogger(MvcContextListener.class);
	
//	/**
//	 * other file directory or jar file that need be added in to CLASSPATH.
//	 */
//	private static final String CUSTOM_CLASSPATH="j.jave.framework.CUSTOM_CLASSPATH";
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			JClassPathList classPathList=JClassPathUtils.getRuntimeClassPathList();
			String webLib= sce.getServletContext().getRealPath("/WEB-INF/lib");
			LOGGER.info("WEB LIB PATH : "+webLib );
			File libFile=new File(webLib);
			if(libFile.exists()){
				if(!classPathList.contains(libFile.getAbsolutePath())){
					classPathList.add(libFile.getAbsolutePath());
					LOGGER.info("Appending [WEB-INF/lib] as classpath : "+libFile.getAbsolutePath());
				}
//				File[] files=libFile.listFiles();
//				if(files!=null){
//					for (int i = 0; i < files.length; i++) {
//						File file=files[i];
//						libStringBuffer.append(";"+(webLib+"/"+file.getName()));
//					}
//				}
			}
//			String customClassPath= sce.getServletContext().getInitParameter(CUSTOM_CLASSPATH);
//			LOGGER.info("Custom CLASSPATH added : "+customClassPath);
//			
//			if(JStringUtils.isNotNullOrEmpty(customClassPath)){
//				customClassPath="";
//			}
			
			String classesPath= sce.getServletContext().getRealPath("/WEB-INF/classes");
			File classesFile=new File(classesPath);
			if(classesFile.exists()){
				if(!classPathList.contains(classesFile.getAbsolutePath())){
					classPathList.add(classesFile.getAbsolutePath());
					LOGGER.info("Appending [WEB-INF/classes] as classpath : "+classesFile.getAbsolutePath());
				}
			}
			System.setProperty("java.class.path", classPathList.represent());
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
