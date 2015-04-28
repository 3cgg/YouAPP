package j.jave.framework.components.web.listener;

import j.jave.framework.support.sqlloader.JSQLConfigure;
import j.jave.framework.support.sqlloader.ddl.JSQLDDLCreateFactory;
import j.jave.framework.utils.JStringUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLDDLListener implements ServletContextListener {
	private static final Logger LOGGER=LoggerFactory.getLogger(SQLDDLListener.class);
	
	private static final String DDL_CREATE_FACTORY="j.jave.framework.support.sqlloader.ddl.JSQLDDLCreateFactory.implementation";
	
	private static final String DDL_CREATE_FACTORY_PACKAGE="j.jave.framework.support.sqlloader.ddl.JSQLDDLCreateFactory.package";
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			String obj=sce.getServletContext().getInitParameter(DDL_CREATE_FACTORY);
			String packString=sce.getServletContext().getInitParameter(DDL_CREATE_FACTORY_PACKAGE);
			Class<?> clazz= Thread.currentThread().getContextClassLoader().loadClass(obj);
			JSQLDDLCreateFactory createFactory=(JSQLDDLCreateFactory) clazz.newInstance();
			JSQLConfigure configure=(JSQLConfigure) createFactory;
			if(JStringUtils.isNotNullOrEmpty(packString)){
				configure.setPackageName(packString);
			}
			createFactory.getObject().create();
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}
