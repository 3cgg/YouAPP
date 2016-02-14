package j.jave.kernal.jave.json;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.type.TypeReference;


/**
 * JJSON class contains a single self.  
 * All operation related JSON can be processed through the class. 
 * @author Administrator
 *
 */
public class JJSON {
	
	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	{
		//always default
		mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
	}
	private static JJSON json;
	
	private JJSON(){}
	
	/**
	 * a default JSON returned,the JSON use default configuration in the commons-json.properties under the class path,
	 * the method in the level of the platform scope.
	 * @return
	 */
	public static final JJSON get(){
		if(json==null){
			synchronized (JJSON.class) {
				if(json==null){
					json=new JJSON();
					String dateFormat=JConfiguration.get().getString(JProperties.JSON_DEFAULT_DATE_FORMAT, "yyyy-MM-dd HH:mm:ss");
					json.mapper.setDateFormat(new SimpleDateFormat(dateFormat));
				}
			}
		}
		return json;
	}
	
	/**
	 * use the passed configuration to construct a JSON utilization,the method is only temporally to fit with your requirement,
	 * must not be used in the platform scope. 
	 * @param config
	 * @return
	 */
	public static JJSON getJSON(JJSONConfig config){
		JJSON configJson=new JJSON();
		String dateFormat=config.getDateFormat();
		if(JStringUtils.isNotNullOrEmpty(dateFormat)){
			configJson.mapper.setDateFormat(new SimpleDateFormat(dateFormat));
		}
		return configJson;
	}
	
	/**
	 * Parse a string 
	 * @param string
	 * @return
	 */
	public Map<String, String> parse(String string){
		try {
			return mapper.readValue(string, new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}  
	
	/**
	 * Parse a string to Object . 
	 * @param string
	 * @param t
	 * @return
	 */
	public <T> T parse(String string, Class<T> t){
		try {
			return mapper.readValue(string, t);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}  
	
	/**
	 * Parse a string to Object . 
	 * @param string
	 * @param typeReference
	 * @return
	 */
	public <T> T parse(String string, TypeReference<T> typeReference){
		try {
			return mapper.readValue(string, typeReference);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}  

	/**
	 * format object to string
	 * <strong>Replace by {@link #formatObject(Object)} due to any potential implicit invoke.</strong>
	 * @param object
	 * @return
	 */
	@Deprecated
	public String format(Object object){
		try {
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			mapper.writeValue(out, object);
			return out.toString("UTF-8");
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * format object via call the method {@code serializableJSONObject} in the class {@link JJSONObject}.
	 * <strong>Replace by {@link #formatJSONObject(JJSONObject)} due to any potential implicit invoke.</strong>
	 * @param jsonObject
	 * @param self whether serialize the object self or not, if true,as same as {@link JJSON#format(Object)}
	 * @return
	 */
	@Deprecated
	public String format(JJSONObject<?> jsonObject,Boolean ... self){
		try {
			JAssert.state(JCollectionUtils.hasInArray(self)&&self.length==1, "only one true / false is supported.");
			boolean isSelf=false;
			if(JCollectionUtils.hasInArray(self)){
				isSelf=self[0];
			}
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			mapper.writeValue(out, isSelf?jsonObject:jsonObject.serializableJSONObject());
			return out.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * format object via call the method {@code serializableJSONObject} in the class {@link JJSONObject}.
	 * <strong>Replace by {@link #formatJSONObject(JJSONObject)} due to any potential implicit invoke.</strong>
	 * @param jsonObject
	 * @return
	 */
	@Deprecated
	public String format(JJSONObject<?> jsonObject){
		try {
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			mapper.writeValue(out, jsonObject.serializableJSONObject());
			return out.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * format object returned by the method {@link JJSONObject#serializableJSONObject()}
	 * @param jsonObject
	 * @return
	 */
	public String formatJSONObject(JJSONObject<?> jsonObject){
		try {
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			mapper.writeValue(out, jsonObject.serializableJSONObject());
			return out.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	/**
	 * format object to string
	 * @param object
	 * @return
	 */
	public String formatObject(Object object){
		try {
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			mapper.writeValue(out, object);
			return out.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	

	
}	

