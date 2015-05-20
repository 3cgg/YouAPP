/**
 * 
 */
package j.jave.framework.components.web.multi.platform.support;

import j.jave.framework.components.support.memcached.subhub.MemcachedService;
import j.jave.framework.components.web.multi.platform.filter.JLinkedRequestFilter;
import j.jave.framework.components.web.utils.HTTPUtils;
import j.jave.framework.servicehub.JServiceHubDelegate;
import j.jave.framework.utils.JStringUtils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * the class support the case of multi-request, 
 * <p>when you store the request successfully,  the method {@link #next()} is to notify the client to next request,
 * if any error occurs in the store phase, the method {@link #terminate()} is to notify the client to stop request.
 * <strong>Note that the method {@link #store()} should be executed only one time.</strong> if calls the method in one instance more than one times, 
 * the request parameters is duplicated.
 * <strong>Also note the instance is not thread safety</strong>
 * @author J
 * @see JLinkedRequestResponse
 * @see JLinkedRequestFilter
 */
public class JLinkedRequestSupport {

	private static final Logger LOGGER=LoggerFactory.getLogger(JLinkedRequestSupport.class);
	
	@SuppressWarnings("unused")
	private final HttpServletRequest request;
	
	/**
	 * generally for this key followed by a unique value.
	 */
	private static final String linkedUniqueKey="j.j.linked.key";

	/**
	 * the key indicates the request is one part of the whole request.
	 * it only appears in the start or end. i.e. only the two items below supported:
	 * <p>j.j.linked.order=start
	 * <p>j.j.linked.order=end
	 */
	private static final String linkedOrderKey="j.j.linked.order";
	
	private static final String started="start";
	
	private static final String ended="end";

	/**
	 * store the actual value in the key-value form,of the key {@link #linkedUniqueKey}
	 */
	private String linkedUniqueValue=null;

	private boolean isStart;
	
	private boolean isEnd;
	
	private static MemcachedService memcachedService=JServiceHubDelegate.get().getService(new Object(), MemcachedService.class);
	
	private Exception exception;
	
	private String actualParams;
	
	boolean isStored=false;
	
	public JLinkedRequestSupport(HttpServletRequest request) {
		this.request=request;
		linkedUniqueValue=request.getParameter(linkedUniqueKey);
		String replacer=linkedUniqueKey+"="+linkedUniqueValue+"&";
		
		String order=request.getParameter(linkedOrderKey);
		if(JStringUtils.isNotNullOrEmpty(order)){
			order=order.trim();
			if(started.equals(order)){
				isStart=true;
			}
			else if(ended.equals(order)){
				isEnd=true;
			}
			replacer=replacer+linkedOrderKey+"="+order+"&";
		}
		String queryString=request.getQueryString();
		actualParams=queryString.replace(replacer, "");
		
	}
	
	public boolean isLinked(){
		return JStringUtils.isNotNullOrEmpty(linkedUniqueValue);
	}
	
	public boolean isStart(){
		return isStart;
	}
	
	public boolean isEnd(){
		return isEnd;
	}
	
	public static boolean isLinked(HttpServletRequest request){
		return JStringUtils.isNotNullOrEmpty(request.getParameter(linkedUniqueKey));
	}
	
	public static boolean isEnd(HttpServletRequest request){
		String order=request.getParameter(linkedOrderKey);
		if(JStringUtils.isNotNullOrEmpty(order)){
			order=order.trim();
			return ended.equals(order);
		}
		return false;
	}
	
	public static boolean isStart(HttpServletRequest request){
		String order=request.getParameter(linkedOrderKey);
		if(JStringUtils.isNotNullOrEmpty(order)){
			order=order.trim();
			return started.equals(order);
		}
		return false;
	}
	
	/**
	 * store the link in the cache, only in the case of that the request is not the last one of the linked. 
	 * @return true if stored successfully
	 * @throws IllegalStateException  if the request is the last one, or the method called multiple times.
	 */
	public boolean store(){
		boolean stored=true;
		try{
			if(!isEnd()){
				
				if(this.isStored){
					throw new IllegalStateException("cannot store parameters more than one times.");
				}
				
				String object=(String) memcachedService.get(linkedUniqueValue);
				object=JStringUtils.isNotNullOrEmpty(actualParams)?((object==null?"":object)+actualParams):actualParams;
				memcachedService.set(linkedUniqueValue, 60, object);
				this.isStored=true;
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug(linkedUniqueValue+"--put-->"+actualParams);
				}
			}
			else{
				throw new IllegalStateException("the request is the last one");
			}
		}catch(Exception e){
			stored=false;
			exception=e;
		}
		return stored;
	}
	
	/**
	 * get the whole request , it should be called when the request is the last. i..e true returned from {@link #isEnd()}
	 * <p>Note the method delete all the parameters associate to the linked request.
	 * @return  all the parameters associate to the linked request.
	 * @throws IllegalStateException if the request is not the last one of the linked request.
	 */
	public String get(){
		
		if(!isEnd()){
			throw new IllegalStateException("the request is not the last of the linked request.");
		}
		String object=(String) memcachedService.get(linkedUniqueValue);
		memcachedService.delete(linkedUniqueValue);
		object=JStringUtils.isNotNullOrEmpty(actualParams)?((object==null?"":object)+actualParams):(object==null?"":object);
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug(linkedUniqueValue+"-get->"+actualParams);
			LOGGER.debug(linkedUniqueValue+"-the whole parameter->"+object);
			LOGGER.debug(linkedUniqueValue+"-the whole parameter->"+HTTPUtils.decode(object)); 
		}
		return object;
	}
	
	
	
	/**
	 * generally, the method will be called when then method {@link #store()} returns true.
	 * @return
	 */
	public JLinkedRequestResponse next(){
		JLinkedRequestResponse multiRequest=new JLinkedRequestResponse();
		multiRequest.setNext(true);
		multiRequest.setMessage("Do next.");
		return multiRequest;
	}
	
	/**
	 * generally, the method will be called when then method {@link #store()} returns false.
	 * @return
	 */
	public JLinkedRequestResponse terminate(){
		JLinkedRequestResponse multiRequest=new JLinkedRequestResponse();
		multiRequest.setNext(false);
		multiRequest.setMessage(exception.getMessage());
		return multiRequest;
	}
	
	
}
