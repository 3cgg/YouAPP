/**
 * 
 */
package j.jave.framework.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @author J
 *
 */
public abstract class JCollectionUtils {

	/**
	 * check whether any element exists 
	 * @param object
	 * @return
	 */
	public static boolean hasInArray(Object[] object) {
		if (object == null)
			return false;
		else
			return object.length > 0;
	}

	/**
	 * check whether any element exists 
	 * @param object
	 * @return
	 */
	public static boolean hasInCollect(Collection<?> object) {
		if (object == null)
			return false;
		else
			return object.size() > 0;
	}

	/**
	 * check whether any element exists 
	 * @param object
	 * @return
	 */
	public static boolean hasInMap(Map<?,?> object) {
		if (object == null)
			return false;
		else
			return object.size() > 0;
	}
	
	/**
	 * check whether any element exists 
	 * @param object
	 * @return
	 */
	public static boolean hasInbytes(byte[] bytes) {
		if (bytes == null)
			return false;
		else
			return bytes.length > 0;
	}
	
}
