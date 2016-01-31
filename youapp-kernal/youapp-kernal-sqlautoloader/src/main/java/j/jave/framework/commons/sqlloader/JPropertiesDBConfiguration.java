/**
 * 
 */
package j.jave.framework.commons.sqlloader;

import j.jave.framework.commons.io.JClassRootPathResolver;
import j.jave.framework.commons.io.JFileResource;
import j.jave.framework.commons.io.JInputStreamWrapperSource;
import j.jave.framework.commons.utils.JPropertiesUtils;
import j.jave.framework.commons.utils.JStringUtils;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;

/**
 * default DB properties configuration.
 * @author J
 */
public class JPropertiesDBConfiguration implements JPropertiesDBConfigure {
	
	private String url;
	
	private String driver;
	
	private String userName;
	
	private String password;
	
	private boolean auto;
	
	private String dialect;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	/**
	 * if the parameter passed in is null , get properties from the default file : "jramework-db.properties" in the root class path.
	 * @param inputStream 
	 */
	@Override
	public JPropertiesDBConfiguration parse(InputStream inputStream) {
		try{
			Properties properties= null;
			if(inputStream==null){
				URI path=new JClassRootPathResolver("jramework-db.properties").resolver();
				properties= JPropertiesUtils.loadProperties(new JFileResource(new File(path)));
			}
			else{
				properties=JPropertiesUtils.loadProperties(new JInputStreamWrapperSource(inputStream));
			}
			
			String driver=JPropertiesUtils.getKey(DRIVER, properties);
			this.setDriver(driver);
			
			String url=JPropertiesUtils.getKey(URL, properties);
			this.setUrl(url);

			String userName=JPropertiesUtils.getKey(USER_NAME, properties);
			this.setUserName(userName);
			
			String password=JPropertiesUtils.getKey(PASSWORD, properties);
			this.setPassword(password);
			
			String auto=JPropertiesUtils.getKey(SQL_AUTO, properties);
			if(JStringUtils.isNullOrEmpty(auto)){
				auto="false";
			}
			this.setAuto(Boolean.valueOf(auto));
			
			String dialect=JPropertiesUtils.getKey(DIALECT, properties);
			this.setDialect(dialect);
		}catch(Exception e){
			return null;
		}
		return this;
	}
	
	
}
