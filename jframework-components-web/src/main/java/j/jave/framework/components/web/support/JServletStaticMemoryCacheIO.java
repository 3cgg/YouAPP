/**
 * 
 */
package j.jave.framework.components.web.support;

import j.jave.framework.io.memory.JStaticMemoryCacheIO;

/**
 * 
 * @author J
 */
public interface JServletStaticMemoryCacheIO extends JStaticMemoryCacheIO {
	
	public static final String JServletStaticMemoryCacheIO_KEY="j.jave.framework.components.web.support.JServletStaticMemoryCacheIO";
	
	JServletContext getServletContext();
	
	JServletContext setServletContext();
}
