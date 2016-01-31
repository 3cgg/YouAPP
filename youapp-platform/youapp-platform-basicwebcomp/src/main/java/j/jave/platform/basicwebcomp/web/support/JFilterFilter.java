/**
 * 
 */
package j.jave.platform.basicwebcomp.web.support;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;

/**
 * 
 * first call {@link #filter(Class)} , then call {@link #filter(FilterRegistration, ServletContext)},
 * note the order above of doing filter.
 * @author J
 */
public interface JFilterFilter {

	/**
	 * if filter on the filter,  return true if filtered , otherwise false.
	 * @param clazz  all filter implement {@link Filter}
	 * @return true|false
	 */
	boolean filter(Class<?> clazz);
	
	/**
	 * return true if filtered , otherwise false.
	 * @param filterRegistration
	 * @param servletContext
	 * @return true|false
	 */
	boolean filter(FilterRegistration filterRegistration,ServletContext servletContext);


}
