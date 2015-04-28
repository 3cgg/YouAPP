/**
 * 
 */
package j.jave.framework.utils;

import java.util.UUID;

/**
 * @author J
 */
public abstract class JUniqueUtils {

	public static String unique() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
