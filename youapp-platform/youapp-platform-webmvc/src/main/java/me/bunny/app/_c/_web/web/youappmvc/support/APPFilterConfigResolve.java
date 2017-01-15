/**
 * 
 */
package me.bunny.app._c._web.web.youappmvc.support;

import javax.servlet.FilterConfig;

/**
 * resolve some path ,or others .
 * @author J
 */
@Deprecated
public class APPFilterConfigResolve implements APPFilterConfig{
	
	public String resolveServiceOnServletPath(FilterConfig filterConfig){
		String serviceServicePath="";
		String tempPath=filterConfig.getInitParameter(SERVICE_ON_SERVLET_PATH);
		if(tempPath!=null){
			tempPath=tempPath.trim();
			if(tempPath.endsWith("/*")){
				serviceServicePath=tempPath.substring(0, tempPath.length()-2);
			}
			else if(tempPath.endsWith("/")){
				serviceServicePath=tempPath.substring(0, tempPath.length()-1);
			}
			else {
				serviceServicePath=tempPath;
			}
		}
		return serviceServicePath;
	}
	
	
}
