/**
 * 
 */
package me.bunny.kernel._c.support._package;

import java.util.Set;

import me.bunny.kernel._c.support.JScanner;

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
