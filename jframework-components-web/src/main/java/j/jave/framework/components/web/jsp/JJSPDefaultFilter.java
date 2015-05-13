package j.jave.framework.components.web.jsp;

import j.jave.framework.utils.JStringUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * do with root URL with slash appended, forward to index page. 
 * @author J
 */
public class JJSPDefaultFilter implements Filter  {

	/**
	 * "/web/service/dispatch/*" pattern configured in web.xml . 
	 */
	private String serviceServletPath="/default";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String tempPath=filterConfig.getInitParameter("serviceServletPath");
		if(tempPath!=null){
			tempPath=tempPath.trim();
			if(tempPath.endsWith("/*")){
				this.serviceServletPath=tempPath.substring(0, tempPath.length()-2);
			}
			else if(tempPath.endsWith("/")){
				this.serviceServletPath=tempPath.substring(0, tempPath.length()-1);
			}
			else {
				this.serviceServletPath=tempPath;
			}
		}
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req=(HttpServletRequest) request;
		String contextPath=req.getContextPath();
		String target=req.getRequestURI().substring(contextPath.length());
		
		if(JStringUtils.isNullOrEmpty(target)||"/".equals(target)){ // only type root  path 
			req.getRequestDispatcher(serviceServletPath+"/login.loginaction/index").forward(req, response);
			return ;
		}
		chain.doFilter(request, response); 
	}

	@Override
	public void destroy() {
		
	}

}
