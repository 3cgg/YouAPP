/**
 * 
 */
package j.jave.framework.components.web.multi.platform.support;

import javax.servlet.http.HttpServletRequest;

/**
 * platform support.
 * @author J
 */
public class PlatformSupport {
	
	private static final String REQUEST_SOURCE="j.jave.request.source";
	
	Platform resolvePlatform(HttpServletRequest request){
		String source=request.getParameter(REQUEST_SOURCE);
		Platform platform=Platform.parse(source);
		return platform;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
