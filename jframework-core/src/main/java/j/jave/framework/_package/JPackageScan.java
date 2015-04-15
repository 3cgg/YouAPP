/**
 * 
 */
package j.jave.framework._package;

import java.util.Set;

/**
 * @author J
 *
 */
public interface JPackageScan {

	/**
	 * 需要缓存这些类 
	 * @return
	 */
	public Set<Class<?>> scan() ;
	
}
