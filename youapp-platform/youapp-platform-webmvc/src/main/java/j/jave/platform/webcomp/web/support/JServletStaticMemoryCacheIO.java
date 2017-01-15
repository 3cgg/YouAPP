/**
 * 
 */
package j.jave.platform.webcomp.web.support;

import me.bunny.kernel.jave.io.memory.JStaticMemoryCacheIO;

/**
 * 
 * @author J
 */
public interface JServletStaticMemoryCacheIO extends JStaticMemoryCacheIO {
	
	public static final String JServletStaticMemoryCacheIO_KEY="j.jave.framework.components.web.support.JServletStaticMemoryCacheIO";
	
	JServletContext getServletContext();
	
	JServletContext setServletContext();
}
