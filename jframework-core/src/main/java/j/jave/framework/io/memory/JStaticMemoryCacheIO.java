package j.jave.framework.io.memory;



/**
 * initial some data to put them into memory.
 * in generally, it can be used together with Memory Cache Utils, like {@code Ehcache}.
 * After initialized, the memory data cannot be changed never. 
 * @author J
 * @param <T> the object put into memory. 
 */
public interface JStaticMemoryCacheIO<T> {
	
	/**
	 * initial some data , and put them into memory, then return them to the caller. 
	 * @return
	 */
	T set();
	
	/**
	 * return the object from the memory. 
	 * @return
	 */
	T get();
	
}
