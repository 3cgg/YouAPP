package j.jave.framework.components.web.multi.platform.filter;

import j.jave.framework.components.web.multi.platform.support.APPFilterConfig;
import j.jave.framework.components.web.multi.platform.support.FilterResponse;
import j.jave.framework.components.web.multi.platform.support.JLinkedRequestSupport;
import j.jave.framework.components.web.support.JFilter;
import j.jave.framework.json.JJSON;
import j.jave.framework.utils.JStringUtils;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * 
 * @author J
 */
public class JLinkedRequestFilter implements JFilter ,APPFilterConfig  {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req=(HttpServletRequest) request;
		
		DispatcherType dispatcherType= req.getDispatcherType();
		
		// check if the request is linked.
		if(dispatcherType==DispatcherType.REQUEST&&JStringUtils.isNotNullOrEmpty(req.getPathInfo())){
			
			if(JLinkedRequestSupport.isLinked(req)){
				
				//the request part of the linked ends 
				if(JLinkedRequestSupport.isEnd(req)){
					chain.doFilter(request, response);
				} 
				else{
					// store the parameter in the temporary cache ,  for distribution system , which we need the cache support  
					
					JLinkedRequestSupport linkedRequestSupport=new JLinkedRequestSupport(req);
					boolean stored=linkedRequestSupport.store();
					
					if(stored){
						// notify the client continue to transfer the rest data.
						
						FilterResponse filterResponse= FilterResponse.newMultiRequest();
						filterResponse.setObject(linkedRequestSupport.next());
						response.getOutputStream().write(JJSON.get().format(filterResponse).getBytes("utf-8"));
					}
					else{
						FilterResponse filterResponse= FilterResponse.newMultiRequest();
						filterResponse.setObject(linkedRequestSupport.terminate());
						// notify the client continue to transfer the rest data.
						response.getOutputStream().write(JJSON.get().format(filterResponse).getBytes("utf-8"));
					}
					
				}
			}
			// always do next in the chain 
			else{
				chain.doFilter(request, response);
			}
		}
		// always let request that is not directly from browser passed.
		else{
			chain.doFilter(request, response);
		}
		
	}

	@Override
	public void destroy() {
		
	}

}
