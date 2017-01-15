/**
 * 
 */
package me.bunny.app._c._web.web.support;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;

/**
 * 
 * first call {@link #filter(Class)} , then call {@link #filter(FilterRegistration, ServletContext)},
 * note the order above of doing filter.
 * @author J
 */
public interface JServletFilter {

	/**
	 * if filter on the http servlet,  return true if filtered , otherwise false.
	 * @param clazz  all http servlet implement {@link HttpServlet}
	 * @return true|false
	 */
	boolean filter(Class<?> clazz);
	
	/**
	 * return true if filtered , otherwise false.
	 * @param servletRegistration
	 * @param servletContext
	 * @return true|false
	 */
	boolean filter(ServletRegistration servletRegistration,ServletContext servletContext);


}
