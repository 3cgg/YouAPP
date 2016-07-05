/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.subhub.servletconfig;

import org.springframework.stereotype.Service;


/**
 * implements ServletConfigService , see that for detail information.
 * @author J
 */
@Service(value=DefaultServletConfigServiceImpl.BEAN_NAME)
public class DefaultServletConfigServiceImpl implements ServletConfigService {
	
	public static final String BEAN_NAME="defaultServletConfigServiceImpl";
	
	@Override
	public String getLoginPath() {
		return "/controller.login/login";
	}
	
	@Override
	public String getLoginoutPath() {
		return "/controller.loginout/loginout";
	}

	@Override
	public String getToLoginPath() {
		return getLoginPath();
	}

	@Override
	public String getEntranceViewPath() {
		return "entrance view url.";
	}

	@Override
	public String getInvalidPathInfo() {
		return "the request url not found.";
	}

}
