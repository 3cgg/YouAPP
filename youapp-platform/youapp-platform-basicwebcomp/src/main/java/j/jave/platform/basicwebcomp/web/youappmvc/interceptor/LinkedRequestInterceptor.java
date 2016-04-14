package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.support.LinkedRequestSupport;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * The filter is purpose for processing the case of the one request is part of the whole request.
 *  i.e. the original request is larger for requesting only once.
 *  The filter is only for GET request, the filter should only intercept the dispatch type of DispatcherType.REQUEST
 * <strong>Note the order of filters is important for how to processing a request.</strong>
 * The filter is designed following by MemoryHTMLFilter. 
 * <p> What request is a part of link ones, the query string must contain the one or all of the below parameters:
 * <pre>
 * 1. {@link LinkedRequestSupport#linkedUniqueKey} mandatory
 * 2. {@link LinkedRequestSupport#linkedOrderKey} optional
 * </pre>
 * see  JLinkedRequestSupport for detail.
 * @author J
 * @see LinkedRequestSupport
 */
public class LinkedRequestInterceptor implements ServletRequestInterceptor {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(LinkedRequestInterceptor.class);
	
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		
		HttpServletRequest req= servletRequestInvocation.getHttpServletRequest();
		HttpServletResponse response=servletRequestInvocation.getHttpServletResponse();
		
		try{
			DispatcherType dispatcherType= req.getDispatcherType();
			
			// check if the request is linked.
			if(dispatcherType==DispatcherType.REQUEST
					&&JStringUtils.isNotNullOrEmpty(req.getPathInfo())
					&&"GET".equals(req.getMethod())){
				
				if(LinkedRequestSupport.isLinked(req)){
					
					//the request part of the linked ends 
					if(LinkedRequestSupport.isEnd(req)){
						//PUT linked parameters in to request scope.
						LinkedRequestSupport linkedRequestSupport=new LinkedRequestSupport(req);
						linkedRequestSupport.setLinkedParameters();
						return servletRequestInvocation.proceed();
					} 
					else{
						// store the parameter in the temporary cache ,  for distribution system , which we need the cache support  
						
						LinkedRequestSupport linkedRequestSupport=new LinkedRequestSupport(req);
						boolean stored=linkedRequestSupport.store();
						
						if(stored){
							// notify the client continue to transfer the rest data.
							
							ResponseModel filterResponse= ResponseModel.newLinkedRequest();
							filterResponse.setData(linkedRequestSupport.next());
//							response.getOutputStream().write(JJSON.get().formatObject(filterResponse).getBytes("utf-8"));
							return filterResponse;
						}
						else{
							ResponseModel filterResponse= ResponseModel.newLinkedRequest();
							filterResponse.setData(linkedRequestSupport.terminate());
							// notify the client continue to transfer the rest data.
							return filterResponse;
//							response.getOutputStream().write(JJSON.get().formatObject(filterResponse).getBytes("utf-8"));
						}
						
					}
				}
				// always do next in the chain 
				else{
					return servletRequestInvocation.proceed();
				}
			}
			// always let request that is not directly from browser passed.
			else{
				return servletRequestInvocation.proceed();
			}
			
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return ServletExceptionUtil.exception(req, response, e);
		}
	}

}
