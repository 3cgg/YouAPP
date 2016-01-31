/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.multi.platform.scope;

import javax.servlet.http.HttpServletRequest;

/**
 * any sub-class can change the {@link HttpServletRequest } context. 
 * @author J
 */
public interface ServletRenderContext {

	public void changeRenderContext(HttpServletRequest request);
	
}
