/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig;

import j.jave.kernal.jave.service.JService;
import j.jave.platform.basicwebcomp.access.subhub.AuthenticationAccessService;
import j.jave.platform.basicwebcomp.web.youappmvc.filter.ValidPathFilter;

/**
 * the interface to make the rule of what common configuration to configure,
 * these information can be used in different Filters or Servlets. 
 * @author J
 * @see ValidPathFilter
 */
public interface ServletConfigService extends JService {
	
	/**
	 * get login path , the path will process the login logic.
	 * @return
	 */
	String getLoginPath();
	
	/**
	 * get login out path , the path will process the login out logic.
	 * @return
	 */
	String getLoginoutPath();
	
	/**
	 * get to login path.  what the path means the end-user need login the system first before doing any other something.
	 * now the method is the same as {@link #getLoginPath()} instead of.
	 * @return String 
	 */
	@Deprecated
	String getToLoginPath();
	
	/**
	 * the default path , the first entrance via which the page will show.
	 * @return
	 */
	@Deprecated
	String getEntranceViewPath();
	
	/**
	 * warning message when the path is invalid.
	 * see {@link AuthenticationAccessService#isValidResource(String)} to conform what URL is valid or not. 
	 * @return warning message. 
	 */
	String getInvalidPathInfo();
	
}
