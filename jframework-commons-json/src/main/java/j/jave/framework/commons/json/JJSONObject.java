/**
 * 
 */
package j.jave.framework.commons.json;

import java.io.Serializable;

/**
 * An interface the indicates the Object itself can defined the object can be serialized by {@link JSON}.
 * BEFORE you serialize the object , you need call {@code serializableJSONObject} to get the reriable object. 
 * @author J
 */
public interface JJSONObject <T> extends Serializable {
	
	
	/**
	 * A serializable Object that can be serialize by {@link JJSON}  
	 * @return
	 */
	T serializableJSONObject(); 
	
	/**
	 * read a JSON string, convert to the object . in generally, the target object is self. 
	 * @param serializableJSONObject
	 */
	void readJSONObject(T serializableJSONObject);
	
}
