package j.jave.framework.utils;

import j.jave.framework.io.JResource;
import j.jave.framework.io.JResourceException;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JPropertiesUtils {

	private static final Logger LOGGER=LoggerFactory.getLogger(JPropertiesUtils.class);
	
	/**
	 * load properties from resource 
	 * @param resource
	 * @return
	 */
	public static Properties loadProperties(JResource resource){
		Properties properties=new Properties();
		try {
			properties.load(resource.getInputStream());
		} catch (IOException e) {
			LOGGER.error("cannot load properties:  "+resource.getDescription(), e);
			throw new JResourceException(resource, "load properties",e);
		}
		return properties;
	}
	
	/**
	 * get value string type by key from properties 
	 * @param key
	 * @param properties
	 * @return
	 */
	public static String getKey(String key,Properties properties){
		Object value= properties.get(key);
		if(value!=null) {
			return String.valueOf(value);
		}
		else{
			return null;
		}
	}
	
	
	
	
	
	
	
	
}
