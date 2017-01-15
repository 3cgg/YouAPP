/**
 * 
 */
package me.bunny.app._c._web.web.youappmvc.render;

import javax.servlet.http.HttpServletRequest;

/**
 * any sub-class can change the {@link HttpServletRequest } context. 
 * @author J
 */
public interface ServletRenderContext {

	public void changeRenderContext(HttpServletRequest request);
	
}
