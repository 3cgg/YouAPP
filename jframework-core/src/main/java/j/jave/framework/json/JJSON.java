package j.jave.framework.json;

import java.io.ByteArrayOutputStream;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;


/**
 * JJSON class contains a single self.  
 * All operation related JSON can be processed through the class. 
 * @author Administrator
 *
 */
public class JJSON {
	
	ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
	
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

	
}
