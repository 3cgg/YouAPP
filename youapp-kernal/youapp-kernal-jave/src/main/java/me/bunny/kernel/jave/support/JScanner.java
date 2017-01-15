/**
 * 
 */
package me.bunny.kernel.jave.support;

import me.bunny.kernel.jave.service.JService;



/**
 * scan anything from anywhere.
 * @author J
 *
 */
public interface JScanner<T> extends JService{

	public T scan() ;
	
}
