package j.jave.framework.commons.json;

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
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
	}
	private static JJSON json=new JJSON();
	
	private JJSON(){}
	
	public static final JJSON get(){
		return json;
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
	 * @param object
	 * @return
	 */
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
	 * format object via call the method {@code serializableJSONObject} in the class {@link JJSONObject}
	 * @param jsonObject
	 * @return
	 */
	public String format(JJSONObject<?> jsonObject){
		try {
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			mapper.writeValue(out, jsonObject.serializableJSONObject());
			return out.toString("UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	
	
	
	

	
}
