package j.jave.framework.sqlloader;

import j.jave.framework.io.JClassRootPathResolver;
import j.jave.framework.io.JFileResource;
import j.jave.framework.utils.JPropertiesUtils;
import j.jave.framework.utils.JUtils;

import java.io.File;
import java.net.URI;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesSQLDDLCreateFactory extends JAbstractSQLDDLCreateFactory{

	private static final Logger LOGGER=LoggerFactory.getLogger(PropertiesSQLDDLCreateFactory.class);
	
	private static final String URL="jframework.database.url";
	private static final String DRIVER="jframework.database.driver";
	private static final String USER_NAME="jframework.database.username";
	private static final String PASSWORD="jframework.database.password";
	private static final String DDL_AOTU="jframework.database.ddl.auto";
	
	private static final String H2="org.h2.Driver";
	
	private static final String AUTO="true";
	
	@Override
	public JSQLDDLCreate getObject() {
		try {
			URI path=new JClassRootPathResolver("jramework-db.properties").resolver();
			Properties properties= JPropertiesUtils.loadProperties(new JFileResource(new File(path)));
			String driver=JPropertiesUtils.getKey(DRIVER, properties);
			String url=JPropertiesUtils.getKey(URL, properties);
			String userName=JPropertiesUtils.getKey(USER_NAME, properties);
			String password=JPropertiesUtils.getKey(PASSWORD, properties);
			String ddlAuto=JPropertiesUtils.getKey(DDL_AOTU, properties);
			
			if(!AUTO.equals(ddlAuto)){
				return new JEmptySQLDDLCreate(driver, url, userName, password);
			}
			
			if(driver!=null&&H2.equals(driver.trim())){
				// h2
				JH2DBSQLDDLCreate h2=new JH2DBSQLDDLCreate(driver, url, userName, password);
				if(JUtils.isNotNullOrEmpty(jarName)){
					h2.setJarName(jarName);
				}
				if(JUtils.isNotNullOrEmpty(packageName)){
					h2.setPackageName(packageName);
				}
				return h2;
			}
			
		} catch (Exception e) {
			LOGGER.error("in create ddl sql creating factory", e);
		}
		return null;
	}

}
