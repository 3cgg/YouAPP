/**
 * 
 */
package j.jave.framework.commons.support;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Object loop style. link self.
 * @author J
 */
public class JObjectLoop<M extends Comparable<M>,T> {

	private HashMap<M, T> objects=new HashMap<M, T>();
	
	private LinkedList<M> compares=new LinkedList<M>();
	
	/**
	 * put all. 
	 * @param maps
	 */
	public void putAll(Map<M, T> maps){
		compares.addAll(maps.keySet());
		objects.putAll(maps);
		Collections.sort(compares, new Comparator<M>() {
			@Override
			public int compare(M o1, M o2) {
				return o1.compareTo(o2);
			}
		});
	}
	
	
	/**
	 * put one by one
	 * @param compare
	 * @param object
	 */
	public void put(M key,T object){
		compares.add(key);
		objects.put(key, object);
		Collections.sort(compares, new Comparator<M>() {
			@Override
			public int compare(M o1, M o2) {
				return o1.compareTo(o2);
			}
		});
	}
	
	/**
	 * get object .  return first if not found. 
	 * @param key
	 * @return
	 */
	public T get(M key){
		M objectKey=null;
		if(compares!=null){
			for(int i=0;i<compares.size();i++){
				M inner=compares.get(i);
				if(key.compareTo(inner)<=0){
					objectKey=inner;
					break;
				}
			}
		}
		if(objectKey==null){
			objectKey=compares.peek();
		}
		if(objectKey!=null){
			return objects.get(objectKey);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
