/**
 * 
 */
package me.bunny.kernel._c.support;

import me.bunny.kernel._c.service.JService;



/**
 * scan anything from anywhere.
 * @author J
 *
 */
public interface JScanner<T> extends JService{

	public T scan() ;
	
}
