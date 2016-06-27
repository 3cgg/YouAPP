/**
 * 
 */
package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.support.JScanner;

import java.util.Set;

/**
 * scan all classes from any where.
 * @author J
 *
 */
public interface JClassesScanner extends JScanner<Set<Class<?>>>{

	/**
	 * 需要缓存这些类 
	 * @return
	 */
	public Set<Class<?>> scan() ;
	
}
