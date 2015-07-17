package j.jave.framework.commons.utils;

import j.jave.framework.commons.io.JClassRootPathResolver;
import j.jave.framework.commons.io.JFile;
import j.jave.framework.commons.io.JResource;
import j.jave.framework.commons.io.JResourceException;
import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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
	
	/**
	 * get from the property, the file name argument is under the class path
	 * @param key
	 * @param propertyFile the file be loaded from the class path
	 * @return
	 */
	public static String getKey(String key,String propertyFile){
		try {
			JClassRootPathResolver classRootPathResolver=new JClassRootPathResolver(propertyFile);
			File file=new File(classRootPathResolver.resolver());
			if(file.exists()){
				Properties properties=  JPropertiesUtils.loadProperties(new JFile(file));
				return JPropertiesUtils.getKey(key, properties);
			}
			else{
				return null;
			}
		} catch (Exception e) {
			throw new JUtilException(e);
		}
	}
	
	/**
	 * a sorted elements returned
	 * @param properties
	 * @param ascDesc  asc by true, desc by false
	 * @return
	 * @throws Exception
	 */
	public static LinkedHashMap<String, Object> sortKey(final Properties properties, final boolean ascDesc){
		final LinkedHashMap<String, Object> items=new LinkedHashMap<String, Object>();
		try {
			
			List<Object> keys=new ArrayList<Object>(properties.keySet());
			Collections.sort(keys, new Comparator<Object>() {
				@Override
				public int compare(Object o1, Object o2) {
					return ((String)o1).compareTo((String)o2)*(ascDesc?1:-1);
				}
			});
			
			JCollectionUtils.each(keys, new JCollectionUtils.CollectionCallback<Object>() {
				@Override
				public void process(Object value) throws Exception {
					String key=(String) value;
					
					items.put(key, properties.get(value));
				}
			});
		} catch (Exception e) {
			throw new JUtilException(e);
		}
		return items;
	}
	
}
