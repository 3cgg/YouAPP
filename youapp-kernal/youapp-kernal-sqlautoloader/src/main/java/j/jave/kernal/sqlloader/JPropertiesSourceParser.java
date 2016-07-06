/**
 * 
 */
package j.jave.kernal.sqlloader;

import j.jave.kernal.jave.io.JClassRootPathResolver;
import j.jave.kernal.jave.io.JFileResource;
import j.jave.kernal.jave.io.JInputStreamWrapperSource;
import j.jave.kernal.jave.utils.JPropertiesUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

/**
 * @author J
 */
public class JPropertiesSourceParser implements JDBConfigParser {
	
	/**
	 * if the parameter passed in is null , get properties from the default file : "jramework-db.properties" in the root class path.
	 * @param inputStream 
	 */
	@Override
	public JDBConfig parse(InputStream inputStream) {
		JDBConfig config=new JDBConfig();
		try{
			Properties properties= null;
			if(inputStream==null){
				URI path=new JClassRootPathResolver("youapp-db.properties").resolve();
				properties= JPropertiesUtils.loadProperties(new JFileResource(new File(path)));
			}
			else{
				properties=JPropertiesUtils.loadProperties(new JInputStreamWrapperSource(inputStream));
			}
			
			String driver=JPropertiesUtils.getKey(JDBConfigNames.DRIVER, properties);
			config.setDriver(driver);
			
			String url=JPropertiesUtils.getKey(JDBConfigNames.URL, properties);
			config.setUrl(url);

			String userName=JPropertiesUtils.getKey(JDBConfigNames.USER_NAME, properties);
			config.setUserName(userName);
			
			String password=JPropertiesUtils.getKey(JDBConfigNames.PASSWORD, properties);
			config.setPassword(password);
			
			String auto=JPropertiesUtils.getKey(JDBConfigNames.SQL_AUTO, properties);
			if(JStringUtils.isNullOrEmpty(auto)){
				auto="false";
			}
			config.setAuto(Boolean.valueOf(auto));
			
			String dialect=JPropertiesUtils.getKey(JDBConfigNames.DIALECT, properties);
			config.setDialect(dialect);
		}catch(Exception e){
			return null;
		}
		return config;
	}
	
	
}
