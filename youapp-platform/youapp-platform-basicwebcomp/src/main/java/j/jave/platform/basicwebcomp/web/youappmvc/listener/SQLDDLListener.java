package j.jave.platform.basicwebcomp.web.youappmvc.listener;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.sqlloader.JSQLConfigure;
import j.jave.kernal.sqlloader.ddl.JPropertiesSQLDDLCreateFactory;
import j.jave.kernal.sqlloader.ddl.JSQLDDLCreateFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The SQL DDL listener, to load SQL DDL statement automatically, 
 * You can define what DDL Factory ( the property {@link SQLDDLListener#DDL_CREATE_FACTORY}) 
 * scans what package ( the property {@link SQLDDLListener#DDL_CREATE_FACTORY_PACKAGE}). 
 * A default factory (<code>JPropertiesSQLDDLCreateFactory</code> ) and a default package name "j.jave" is used if no any configured.
 * <p><strong> Note that the class <code>SQLDDLListener</code> is purpose for developer case, never used in production.</strong> 
 * @author J
 * @see j.jave.framework.support.sqlloader.ddl.JPropertiesSQLDDLCreateFactory
 */
public class SQLDDLListener implements ServletContextListener {
	private static final JLogger LOGGER=JLoggerFactory.getLogger(SQLDDLListener.class);
	
	private static final String DDL_CREATE_FACTORY="youapp.ddl.create.factory";
	
	private static final String DDL_CREATE_FACTORY_PACKAGE="youapp.ddl.create.factory.scan.package";
	
	private static final String DEFAULT_PACKAGE="j.jave";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			String obj=sce.getServletContext().getInitParameter(DDL_CREATE_FACTORY);
			String packString=sce.getServletContext().getInitParameter(DDL_CREATE_FACTORY_PACKAGE);
			Class<?> clazz=null;
			if(JStringUtils.isNotNullOrEmpty(obj)){
				clazz= JClassUtils.load(obj, Thread.currentThread().getContextClassLoader());
			}
			else{
				clazz=JPropertiesSQLDDLCreateFactory.class;
			}
			
			if(JStringUtils.isNullOrEmpty(packString)){
				packString=DEFAULT_PACKAGE;
			}
			
			JSQLDDLCreateFactory createFactory=(JSQLDDLCreateFactory) clazz.newInstance();
			JSQLConfigure configure=(JSQLConfigure) createFactory;
			if(JStringUtils.isNotNullOrEmpty(packString)){
				configure.setPackageName(packString);
			}
			createFactory.getObject().create();
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
