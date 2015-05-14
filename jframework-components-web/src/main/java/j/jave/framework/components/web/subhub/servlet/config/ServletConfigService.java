/**
 * 
 */
package j.jave.framework.components.web.subhub.servlet.config;

import j.jave.framework.components.web.jsp.JJSPLoginFilter;
import j.jave.framework.components.web.multi.platform.filter.JValidPathFilter;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.servicehub.JService;

/**
 * the interface to make the rule of what common configuration to configure,
 * these information can be used in different Filters or Servlets. 
 * @author J
 * @see JJSPLoginFilter 
 * @see JValidPathFilter
 */
public interface ServletConfigService extends JService {
	
	/**
	 * get login path ,  what the path means in which all validations of logining will be done.
	 * which is used in {@link JJSPLoginFilter}
	 * @return
	 */
	String getLoginPath();
	
	/**
	 * get to login path.  what the path means the end-user need login the system first before doing any other something.
	 * which is used in {@link JJSPLoginFilter} 
	 * @return
	 */
	String getToLoginPath();
	
	/**
	 * the default path , the first entrance via which the page will show.
	 * the path will used in {@link JValidPathFilter}
	 * @return
	 */
	String getEntranceViewPath();
	
	/**
	 * warning message when the path is invalid.
	 * see {@link LoginAccessService#isValidResource(String)} to conform what URL is valid or not. 
	 * @return warning message. 
	 */
	String getInvalidPathInfo();
	
}
