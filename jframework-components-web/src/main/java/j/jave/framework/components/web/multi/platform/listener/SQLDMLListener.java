package j.jave.framework.components.web.multi.platform.listener;

import j.jave.framework.commons.exception.JInitializationException;
import j.jave.framework.commons.reflect.JClassUtils;
import j.jave.framework.commons.sqlloader.JSQLConfigure;
import j.jave.framework.commons.sqlloader.ddl.JPropertiesSQLDDLCreateFactory;
import j.jave.framework.commons.sqlloader.dml.JSQLDMLCreateFactory;
import j.jave.framework.commons.utils.JStringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SQL DML listener, to load SQL DML statement automatically, 
 * You can define what DML Factory ( the property {@link SQLDMLListener#DML_CREATE_FACTORY}) 
 * scans what package ( the property {@link SQLDMLListener#DML_CREATE_FACTORY_PACKAGE}). 
 * A default factory (<code>JPropertiesSQLDMLCreateFactory</code> ) and a default package name "j.jave.framework.components" are used if no any configured.
 * <p><strong> Note that the class <code>SQLDMLListener</code> is purpose for developer case, never used in production.</strong> 
 * @author J
 * @see j.jave.framework.support.sqlloader.dml.JPropertiesSQLDMLCreateFactory
 */
public class SQLDMLListener implements ServletContextListener {
	private static final Logger LOGGER=LoggerFactory.getLogger(SQLDMLListener.class);
	
	private static final String DML_CREATE_FACTORY="j.jave.framework.support.sqlloader.dml.JSQLDMLCreateFactory.implementation";
	
	private static final String DML_CREATE_FACTORY_PACKAGE="j.jave.framework.support.sqlloader.dml.JSQLDMLCreateFactory.package";
	
	private static final String DEFAULT_PACKAGE="j.jave.framework.components";
	
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
