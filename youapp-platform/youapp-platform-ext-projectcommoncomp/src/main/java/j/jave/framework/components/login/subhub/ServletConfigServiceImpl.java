/**
 * 
 */
package j.jave.framework.components.login.subhub;

import org.springframework.stereotype.Service;

/**
 * implements ServletConfigService , see that for detail information.
 * @author J
 */
@Service(value="j.jave.framework.components.login.subhub.ServletConfigServiceImpl")
public class ServletConfigServiceImpl implements ServletConfigService {
	
	@Override
	public String getLoginPath() {
		return "/login.loginaction/login";
	}

	@Override
	public String getToLoginPath() {
		return "/login.loginaction/toLogin";
	}

	@Override
	public String getEntranceViewPath() {
		return "/login.loginaction/index";
	}

	@Override
	public String getInvalidPathInfo() {
		return "the request url not found.";
	}

}
