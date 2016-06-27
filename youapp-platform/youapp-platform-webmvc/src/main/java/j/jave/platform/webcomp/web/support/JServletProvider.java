/**
 * 
 */
package j.jave.platform.webcomp.web.support;

import j.jave.kernal.jave.support._package.JProvider;

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
