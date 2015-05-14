package j.jave.framework.io.memory;



/**
 * dynamically put some data into memory.
 * in generally, it can be used together with Memory Cache Utils, like {@code Ehcache}.
 * @author J
 * @param <T> the object put into memory. 
 */
public interface JSingleDynamicMemoryCacheIO<T> extends JDynamicMemoryCacheIO {
	
	/**
	 * put them into memory, then return them to the caller. 
	 * @return
	 */
	T set(String key,T object);
	
	/**
	 * return the object from the memory. 
	 * @return
	 */
	T get(String key);
	
	/**
	 * remove the object from the memory. 
	 * @return the previous stored data.
	 */
	T remove(String key);
	
}
