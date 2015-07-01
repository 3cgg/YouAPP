package j.jave.framework.commons.utils;

import j.jave.framework.commons.io.JResource;
import j.jave.framework.commons.io.JResourceException;
import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public abstract class JPropertiesUtils {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JPropertiesUtils.class);
	
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
	
	public static interface Process{
		public void process(Object key,Object value,Properties properties) throws Exception;
	}
	
	/**
	 * do with all the properties(key-value form) in the properties.
	 * @param properties
	 * @param process
	 */
	public static void each(Properties properties,Process process) throws Exception{
		Set<Entry<Object, Object>> sets= properties.entrySet();
		for (Iterator<Entry<Object, Object>> iterator = sets.iterator(); iterator
				.hasNext();) {
			Entry<Object, Object> entry = iterator.next();
			process.process(entry.getKey(), entry.getValue(), properties);
		}
	}
	
	
	
	
	
	
}
