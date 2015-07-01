/**
 * 
 */
package j.jave.framework.commons.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public static interface Callback<K,V>{
		void process(K key,V value) throws Exception;
	}
	
	/**
	 * quickly iterator elements of map.
	 * @param map null is the same as empty
	 * @param callback
	 * @throws Exception
	 */
	public  static <K,V> void each(Map<K, V> map, Callback<K, V> callback) throws Exception{
		if(hasInMap(map)){
			for (Iterator<Entry<K, V>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Entry<K, V> entry =  iterator.next();
				callback.process(entry.getKey(), entry.getValue());
			}
		}
	}
	
}
