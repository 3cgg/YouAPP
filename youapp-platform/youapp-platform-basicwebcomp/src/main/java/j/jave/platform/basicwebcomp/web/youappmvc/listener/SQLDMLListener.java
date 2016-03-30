package j.jave.platform.basicwebcomp.web.youappmvc.listener;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.sqlloader.JSQLConfigure;
import j.jave.kernal.sqlloader.ddl.JPropertiesSQLDDLCreateFactory;
import j.jave.kernal.sqlloader.dml.JSQLDMLCreateFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * The SQL DML listener, to load SQL DML statement automatically, 
 * You can define what DML Factory ( the property {@link SQLDMLListener#DML_CREATE_FACTORY}) 
 * scans what package ( the property {@link SQLDMLListener#DML_CREATE_FACTORY_PACKAGE}). 
 * A default factory (<code>JPropertiesSQLDMLCreateFactory</code> ) and a default package name "j.jave" is used if no any configured.
 * <p><strong> Note that the class <code>SQLDMLListener</code> is purpose for developer case, never used in production.</strong> 
 * @author J
 * @see j.jave.framework.support.sqlloader.dml.JPropertiesSQLDMLCreateFactory
 */
public class SQLDMLListener implements ServletContextListener {
	private static final JLogger LOGGER=JLoggerFactory.getLogger(SQLDMLListener.class);
	
	private static final String DML_CREATE_FACTORY="youapp.dml.create.factory";
	
	private static final String DML_CREATE_FACTORY_PACKAGE="youapp.dml.create.factory.scan.package";
	
	private static final String DEFAULT_PACKAGE="j.jave";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			String obj=sce.getServletContext().getInitParameter(DML_CREATE_FACTORY);
			String packString=sce.getServletContext().getInitParameter(DML_CREATE_FACTORY_PACKAGE);
			
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
			
			JSQLDMLCreateFactory createFactory=(JSQLDMLCreateFactory) clazz.newInstance();
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
