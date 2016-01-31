package j.jave.framework.components.web.youappmvc.multi.platform.filter;

import j.jave.framework.commons.json.JJSON;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.web.support.JFilter;
import j.jave.framework.components.web.youappmvc.multi.platform.support.APPFilterConfig;
import j.jave.framework.components.web.youappmvc.multi.platform.support.FilterResponse;
import j.jave.framework.components.web.youappmvc.multi.platform.support.JLinkedRequestSupport;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * The filter is purpose for processing the case of the one request is part of the whole request.
 *  i.e. the original request is larger for requesting only once.
 *  The filter is only for GET request, the filter should only intercept the dispatch type of DispatcherType.REQUEST
 * <strong>Note the order of filters is important for how to processing a request.</strong>
 * The filter is designed following by MemoryHTMLFilter. 
 * <p> What request is a part of link ones, the query string must contain the one or all of the below parameters:
 * <pre>
 * 1. {@link JLinkedRequestSupport#linkedUniqueKey} mandatory
 * 2. {@link JLinkedRequestSupport#linkedOrderKey} optional
 * </pre>
 * see  JLinkedRequestSupport for detail.
 * @author J
 * @see JLinkedRequestSupport
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
		if(dispatcherType==DispatcherType.REQUEST
				&&JStringUtils.isNotNullOrEmpty(req.getPathInfo())
				&&"GET".equals(req.getMethod())){
			
			if(JLinkedRequestSupport.isLinked(req)){
				
				//the request part of the linked ends 
				if(JLinkedRequestSupport.isEnd(req)){
					//PUT linked parameters in to request scope.
					JLinkedRequestSupport linkedRequestSupport=new JLinkedRequestSupport(req);
					linkedRequestSupport.setLinkedParameters();
					chain.doFilter(request, response);
				} 
				else{
					// store the parameter in the temporary cache ,  for distribution system , which we need the cache support  
					
					JLinkedRequestSupport linkedRequestSupport=new JLinkedRequestSupport(req);
					boolean stored=linkedRequestSupport.store();
					
					if(stored){
						// notify the client continue to transfer the rest data.
						
						FilterResponse filterResponse= FilterResponse.newLinkedRequest();
						filterResponse.setObject(linkedRequestSupport.next());
						response.getOutputStream().write(JJSON.get().format(filterResponse).getBytes("utf-8"));
					}
					else{
						FilterResponse filterResponse= FilterResponse.newLinkedRequest();
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
