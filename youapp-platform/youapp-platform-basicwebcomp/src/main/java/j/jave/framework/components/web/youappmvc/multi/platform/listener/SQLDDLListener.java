package j.jave.framework.components.web.youappmvc.multi.platform.listener;

import j.jave.framework.commons.exception.JInitializationException;
import j.jave.framework.commons.reflect.JClassUtils;
import j.jave.framework.commons.sqlloader.JSQLConfigure;
import j.jave.framework.commons.sqlloader.ddl.JPropertiesSQLDDLCreateFactory;
import j.jave.framework.commons.sqlloader.ddl.JSQLDDLCreateFactory;
import j.jave.framework.commons.utils.JStringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The SQL DDL listener, to load SQL DDL statement automatically, 
 * You can define what DDL Factory ( the property {@link SQLDDLListener#DDL_CREATE_FACTORY}) 
 * scans what package ( the property {@link SQLDDLListener#DDL_CREATE_FACTORY_PACKAGE}). 
 * A default factory (<code>JPropertiesSQLDDLCreateFactory</code> ) and a default package name "j.jave.framework.components" are used if no any configured.
 * <p><strong> Note that the class <code>SQLDDLListener</code> is purpose for developer case, never used in production.</strong> 
 * @author J
 * @see j.jave.framework.support.sqlloader.ddl.JPropertiesSQLDDLCreateFactory
 */
public class SQLDDLListener implements ServletContextListener {
	private static final Logger LOGGER=LoggerFactory.getLogger(SQLDDLListener.class);
	
	private static final String DDL_CREATE_FACTORY="j.jave.framework.support.sqlloader.ddl.JSQLDDLCreateFactory.implementation";
	
	private static final String DDL_CREATE_FACTORY_PACKAGE="j.jave.framework.support.sqlloader.ddl.JSQLDDLCreateFactory.package";
	
	private static final String DEFAULT_PACKAGE="j.jave.framework.components";
	
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
