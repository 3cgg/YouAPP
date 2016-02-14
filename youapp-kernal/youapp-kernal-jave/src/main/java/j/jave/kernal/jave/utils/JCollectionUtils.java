/**
 * 
 */
package j.jave.kernal.jave.utils;

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
	
	public static interface EntryCallback<K,V>{
		void process(K key,V value) throws Exception;
	}
	
	public static interface CollectionCallback<T>{
		void process(T value) throws Exception;
	}
	
	/**
	 * quickly iterator elements of map.
	 * @param map null is the same as empty
	 * @param callback
	 * @throws Exception
	 */
	public  static <K,V> void each(Map<K, V> map, EntryCallback<K, V> callback) throws Exception{
		if(hasInMap(map)){
			for (Iterator<Entry<K, V>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Entry<K, V> entry =  iterator.next();
				callback.process(entry.getKey(), entry.getValue());
			}
		}
	}

	public static  <T>  void each(Collection<T> collection, CollectionCallback<T> callback) throws Exception{
		if(hasInCollect(collection)){
			for (Iterator<T> iterator = collection.iterator(); iterator.hasNext();) {
				T object=  iterator.next();
				callback.process(object);
			}
		}
	}
	
	/**
	 * check whether the first set contains all elements in the second set or not.
	 * @param obj1  only as primitive type, int , long , float,String etc.
	 * @param obj2  only as primitive type, int , long , float,String etc.
	 * @return
	 */
	public static boolean includeIn(Object[] set,Object[] seeds){
		
		if(set==null
				||seeds==null
				||set.length==0
				||seeds.length==0
				) return false;
		boolean allIncluded=true;
		for (int i = 0; i < seeds.length; i++) {
			Object seed=seeds[i];
			boolean included=false;
			for (int j = 0; j < set.length; j++) {
				Object seedEle=set[j];
				if((included=seed.equals(seedEle))){
					break;
				}
			}
			
			if(!included){
				allIncluded=false;
				break;
			}
		}
		return allIncluded; 
	}
	
}
