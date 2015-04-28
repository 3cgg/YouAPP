package j.jave.framework.support.sqlloader.dml;

import j.jave.framework.io.JClassRootPathResolver;
import j.jave.framework.io.JFileResource;
import j.jave.framework.support.sqlloader.PropertiesDBConfiguration;
import j.jave.framework.utils.JPropertiesUtils;
import j.jave.framework.utils.JStringUtils;

import java.io.File;
import java.net.URI;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesSQLDMLCreateFactory extends JAbstractSQLDMLCreateFactory implements PropertiesDBConfiguration{

	private static final Logger LOGGER=LoggerFactory.getLogger(PropertiesSQLDMLCreateFactory.class);
	
	@Override
	public JSQLDMLCreate getObject() {
		try {
			URI path=new JClassRootPathResolver("jramework-db.properties").resolver();
			Properties properties= JPropertiesUtils.loadProperties(new JFileResource(new File(path)));
			String driver=JPropertiesUtils.getKey(DRIVER, properties);
			String url=JPropertiesUtils.getKey(URL, properties);
			String userName=JPropertiesUtils.getKey(USER_NAME, properties);
			String password=JPropertiesUtils.getKey(PASSWORD, properties);
			String ddlAuto=JPropertiesUtils.getKey(DDL_AOTU, properties);
			
			if(!AUTO.equals(ddlAuto)){
				return new JEmptySQLDMLCreate(driver, url, userName, password);
			}
			
			if(driver!=null&&H2.equals(driver.trim())){
				// h2
				JH2DBSQLDMLCreate h2=new JH2DBSQLDMLCreate(driver, url, userName, password);
				if(JStringUtils.isNotNullOrEmpty(jarName)){
					h2.setJarName(jarName);
				}
				if(JStringUtils.isNotNullOrEmpty(packageName)){
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
