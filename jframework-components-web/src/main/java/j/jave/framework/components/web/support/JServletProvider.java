/**
 * 
 */
package j.jave.framework.components.web.support;

import j.jave.framework.support.detect.JProvider;

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
