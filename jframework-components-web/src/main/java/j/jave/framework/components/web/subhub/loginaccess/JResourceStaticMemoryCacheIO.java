/**
 * 
 */
package j.jave.framework.components.web.subhub.loginaccess;

import j.jave.framework.io.memory.JStaticMemoryCacheIO;

import java.util.List;

/**
 * all resources in the system , from database or potential class information. 
 * Unique Key is : {@value #JResourceStaticMemoryCacheIO_KEY}
 * @author J
 */
public interface JResourceStaticMemoryCacheIO extends JStaticMemoryCacheIO {

	public static final String JResourceStaticMemoryCacheIO_KEY="j.jave.framework.components.web.subhub.loginaccess.JResourceStaticMemoryCacheIO";
	
	/**
	 * set all system path resources in cache, such as "/login.loginaction/login"
	 * <p>KEY :  {@value #JResourceStaticMemoryCacheIO_KEY}
	 * @return all system path resources
	 */
	List<String> setResources();
	
	/**
	 * get all system path resources , such as "/login.loginaction/login"
	 *  <p>KEY :  {@value #JResourceStaticMemoryCacheIO_KEY}
	 * @return all system path resources
	 */
	List<String> getResources();
	
}
