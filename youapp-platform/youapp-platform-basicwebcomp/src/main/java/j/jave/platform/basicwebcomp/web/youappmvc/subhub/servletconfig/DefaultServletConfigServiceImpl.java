/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig;

import org.springframework.stereotype.Service;


/**
 * implements ServletConfigService , see that for detail information.
 * @author J
 */
@Service(value="j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.DefaultServletConfigServiceImpl")
public class DefaultServletConfigServiceImpl implements ServletConfigService {
	
	@Override
	public String getLoginPath() {
		return "/controller.login/login";
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
