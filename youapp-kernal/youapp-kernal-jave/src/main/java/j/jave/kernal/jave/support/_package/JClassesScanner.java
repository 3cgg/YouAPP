/**
 * 
 */
package j.jave.kernal.jave.support._package;

import java.util.Set;

/**
 * scan all classes from any where.
 * @author J
 *
 */
public interface JClassesScanner {

	/**
	 * 需要缓存这些类 
	 * @return
	 */
	public Set<Class<?>> scan() ;
	
}
