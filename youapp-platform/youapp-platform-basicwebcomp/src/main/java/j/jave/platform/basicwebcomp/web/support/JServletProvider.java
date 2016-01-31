/**
 * 
 */
package j.jave.platform.basicwebcomp.web.support;

import j.jave.kernal.jave.support.detect.JProvider;

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
