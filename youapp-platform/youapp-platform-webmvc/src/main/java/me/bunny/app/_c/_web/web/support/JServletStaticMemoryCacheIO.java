/**
 * 
 */
package me.bunny.app._c._web.web.support;

import me.bunny.kernel._c.io.memory.JStaticMemoryCacheIO;

/**
 * 
 * @author J
 */
public interface JServletStaticMemoryCacheIO extends JStaticMemoryCacheIO {
	
	public static final String JServletStaticMemoryCacheIO_KEY="j.jave.framework.components.web.support.JServletStaticMemoryCacheIO";
	
	JServletContext getServletContext();
	
	JServletContext setServletContext();
}
