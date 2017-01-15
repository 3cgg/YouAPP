/**
 * 
 */
package j.jave.platform.webcomp.web.support;

import me.bunny.kernel.jave.support.JProvider;

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
