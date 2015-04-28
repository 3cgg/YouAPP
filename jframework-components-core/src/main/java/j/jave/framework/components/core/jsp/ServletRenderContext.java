/**
 * 
 */
package j.jave.framework.components.core.jsp;

import javax.servlet.http.HttpServletRequest;

/**
 * any sub-class can change the {@link HttpServletRequest } context. 
 * @author J
 */
public interface ServletRenderContext {

	public void changeRenderContext(HttpServletRequest request);
	
}
