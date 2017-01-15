/**
 * 
 */
package me.bunny.app._c._web.web.support;

import me.bunny.kernel._c.support.JProvider;

/**
 * Servlet context provider.
 * @author J
 */
public interface JServletProvider extends JProvider {
	
	
	/**
	 * get custom servlet context information. 
	 * @return
	 */
	JServletContext getServletContext(); 
	
	
}
