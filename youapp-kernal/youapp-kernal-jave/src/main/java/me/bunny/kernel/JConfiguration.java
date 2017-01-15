package me.bunny.kernel;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.utils.JLangUtils;

/**
 * the basic environment configuration 
 * @author J
 *
 */
public class JConfiguration extends HashMap<String, Object>{
	
//	private static final JLogger LOGGER =JLoggerFactory.getLogger(JConfiguration.class);
	
	private final static JConfigMetaMap defaultConfig=new JConfigMetaMap();
	
	/**
	 * use {@link #newInstance()} instead of 
	 * @return
	 */
	@Deprecated
	public static JConfiguration get(){
		return new JConfiguration();
	}
	
	public static JConfiguration newInstance(){
		return new JConfiguration();
	}
	
	public Object get(String  key, Object defaultValue){
		
		if(containsKey(key)){
			return get(key);
		}
		
		if(defaultConfig.containsKey(key)){
			return defaultConfig.get(key).getValue();
		}
		Object value=getFromSystem(key);
		return value==null? defaultValue:value;
	}
	
	private Object getFromSystem(String key){
		Object object=System.getProperty(key);
		return object;
	}
	
	public String getString(String  key){
		Object obj=get(key,null);
		return (String) obj;
	}
	
	public String getString(String  key, String defaultValue){
		Object obj=get(key, defaultValue);
		return (String) obj;
	}
	
	public Boolean getBoolean(String  key, boolean defaultValue){
		Object obj=get(key, defaultValue);
		if(!Boolean.class.isInstance(obj)){
			return JLangUtils.booleanValue(String.valueOf(obj));
		}
		return (Boolean)obj;
	}
	
	public int getInt(String  key, int defaultValue){
		Object obj=get(key, defaultValue);
		if(!Integer.class.isInstance(obj)){
			return Integer.valueOf(String.valueOf(obj));
		}
		return (Integer)obj;
	}
	
	public long getLong(String  key, long defaultValue){
		Object obj=get(key, defaultValue);
		if(!Long.class.isInstance(obj)){
			return Long.valueOf(String.valueOf(obj));
		}
		return (Long)obj;
	}
	
	public double getDouble(String  key, double defaultValue){
		Object obj=get(key, defaultValue);
		if(!Double.class.isInstance(obj)){
			return Double.valueOf(String.valueOf(obj));
		}
		return (Double)obj;
	}
	
	public Set<String> allKeys(String regex){
		Set<String> keys=new HashSet<String>();
		Pattern pattern=Pattern.compile(regex);
		
		for(String key:defaultConfig.keySet()){
			if(pattern.matcher(key).matches()){
				keys.add(key);
			}
		}
		
		for(String key:this.keySet()){
			if(pattern.matcher(key).matches()){
				keys.add(key);
			}
		}
		return keys;
	}
	
	public Map<String, JConfigMeta> getAllConfigMetas(){
		return Collections.unmodifiableMap(defaultConfig);
	}
	
	/**
	 * get the JSON format of all configurations
	 * @return
	 */
	public String getAllConfigMetaJSON(){
		return JJSON.get().formatObject(defaultConfig);
	}
	
	public JConfigMeta getConfigMetas(String key){
		return defaultConfig.get(key);
	}
}
