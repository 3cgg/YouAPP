/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.support;

import j.jave.platform.sps.support.memcached.subhub.MemcachedDelegateService;
import j.jave.platform.webcomp.web.youappmvc.ServletHttpContext;
import j.jave.platform.webcomp.web.youappmvc.interceptor.LinkedRequestInterceptor;
import j.jave.platform.webcomp.web.youappmvc.utils.YouAppMvcUtils;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * the class support the case of multi-request, 
 * <p>when you store the request successfully,  the method {@link #next()} is to notify the client to next request,
 * if any error occurs in the store phase, the method {@link #terminate()} is to notify the client to stop request.
 * <strong>Note that the method {@link #store()} should be executed only one time.</strong> if calls the method in one instance more than one times, 
 * the request parameters is duplicated.
 * <p>The class support two methods to store the parameter , one is pure simple string format , another is a advance pojo object.
 * The first simply store the parameter in the memcached, the second can trace the request , such as the order , the path info ... etc.  
 * Of cause the second resume more memory and less performance. 
 *  
 * <strong>Also note the instance is not thread safety</strong>
 * @author J
 * @see LinkedRequestResponse
 * @see LinkedRequestInterceptor
 * @see SimpleStringTrace
 * @see LinkedObjectTrace
 */
public class LinkedRequestSupport {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(LinkedRequestSupport.class);
	
	private static MemcachedDelegateService memcachedService=JServiceHubDelegate.get().getService(new Object(), MemcachedDelegateService.class);
	
	/**
	 * be used for linked request, the linked request parameter stores in the additional map in request scope, the additional map key is 
	 * {@link ServletHttpContext #ADDITIONAL_PARAM_KEY}
	 */
	public static final String LINKED_REQUEST_PARAM_KEY="j.jave.framework.components.web.linked.param";
	
	private static final String EMPTY="";
	
	public enum TYPE{
		SimpleString,
		LinkedObject
	}
	
	private final HttpServletRequest request;
	
	/**
	 * generally for this key followed by a unique value.
	 */
	public static final String linkedUniqueKey="j.j.linked.key";

	/**
	 * the key indicates the request is one part of the whole request.
	 * it only appears in the start or end. i.e. only the two items below supported:
	 * <p>j.j.linked.order=start
	 * <p>j.j.linked.order=end
	 */
	public static final String linkedOrderKey="j.j.linked.order";
	
	private static final String started="start";
	
	private static final String ended="end";

	/**
	 * store the actual value in the key-value form,of the key {@link #linkedUniqueKey}
	 */
	private String linkedUniqueValue;

	private boolean isStart;
	
	private boolean isEnd;
	
	private Exception exception;
	
	private String actualParams;
	
	boolean isStored=false;
	
	private Trace<?> trace;

	private final TYPE storeType;
	
	public LinkedRequestSupport(HttpServletRequest request,TYPE storeType){
		this.storeType=storeType;
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
		init();
	}
	
	public LinkedRequestSupport(HttpServletRequest request) {
		this(request, TYPE.SimpleString);
	}
	
	private void init(){
		trace=newObject();
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
	
	/**
	 * true if the request parameter {@link #linkedUniqueKey} is not null or empty.
	 * (j.j.linked.key=any)
	 * @param request
	 * @return
	 */
	public static boolean isLinked(HttpServletRequest request){
		return request==null?false:JStringUtils.isNotNullOrEmpty(request.getParameter(linkedUniqueKey));
	}
	
	/**
	 * true if the request parameter {@link #linkedOrderKey}=={@link #ended}
	 * (j.j.linked.order=end)
	 * @param request
	 * @return
	 */
	public static boolean isEnd(HttpServletRequest request){
		String order=request.getParameter(linkedOrderKey);
		if(JStringUtils.isNotNullOrEmpty(order)){
			order=order.trim();
			return ended.equals(order);
		}
		return false;
	}
	
	/**
	 * true if the request parameter {@link #linkedOrderKey}=={@link #started}
	 * (j.j.linked.order=start)
	 * @param request
	 * @return
	 */
	public static boolean isStart(HttpServletRequest request){
		String order=request.getParameter(linkedOrderKey);
		if(JStringUtils.isNotNullOrEmpty(order)){
			order=order.trim();
			return started.equals(order);
		}
		return false;
	}
	
	
	/**
	 * the inner class is purpose for advance tracing for linked request.
	 * @author J
	 */
	@SuppressWarnings("serial")
	public static class LinkedObject implements Serializable{
		int order=0;
		
		String parameter=EMPTY;

		String pathInfo;
		
		boolean isStart;
		
		boolean isEnd;
	}
	
	
	private interface Trace<T>{
		
		/**
		 * store the parameter. 
		 * @return true if the stored successfully, otherwise false, such as any unexpected exception occurs
		 * @throws Exception
		 */
		public boolean store() throws Exception;
		
		/**
		 * the method is for tracing linked request.
		 * @return
		 */
		public T get();
		
		/**
		 * get the set of parameters up till this request.
		 * @return
		 */
		public String getParameter();
	}
	
	private class LinkedObjectTrace implements Trace<LinkedObject>{

		private static final String append="-linked-object-trace";
		private String key=linkedUniqueValue+append;
		private String pathInfo=YouAppMvcUtils.getPathInfo(request);
		@Override
		public boolean store()throws Exception  {
			boolean stored=true;
			LinkedObject object=(LinkedObject) memcachedService.get(key);
			if(object!=null){
				object.order++;
				object.parameter=object.parameter+(JStringUtils.isNullOrEmpty(actualParams)?EMPTY:actualParams);
				object.isEnd=LinkedRequestSupport.this.isEnd;
				object.isStart=LinkedRequestSupport.this.isStart;
			}
			else{
				object=new LinkedObject();
				object.order++;
				object.parameter=object.parameter+(JStringUtils.isNullOrEmpty(actualParams)?EMPTY:actualParams);
				object.isEnd=LinkedRequestSupport.this.isEnd;
				object.isStart=LinkedRequestSupport.this.isStart;
				object.pathInfo=pathInfo;
			}
			memcachedService.set(key, 60, object);
			return stored;
		}

		@Override
		public LinkedObject get() {
			LinkedObject object=(LinkedObject) memcachedService.get(key);
			memcachedService.delete(key);
			if(object!=null){
				object.order++;
				object.parameter=object.parameter+(JStringUtils.isNullOrEmpty(actualParams)?EMPTY:actualParams);
				object.isEnd=LinkedRequestSupport.this.isEnd;
				object.isStart=LinkedRequestSupport.this.isStart;
			}
			else{
				object=new LinkedObject();
				object.order++;
				object.parameter=object.parameter+(JStringUtils.isNullOrEmpty(actualParams)?EMPTY:actualParams);
				object.isEnd=LinkedRequestSupport.this.isEnd;
				object.isStart=LinkedRequestSupport.this.isStart;
				object.pathInfo=pathInfo;
			}
			return object;
		}
		
		@Override
		public String getParameter() {
			return get().parameter;
		}

	}
	
	private class SimpleStringTrace implements Trace<String>{

		@Override
		public boolean store() throws Exception {
			boolean stored=true;
			String object=(String) memcachedService.get(linkedUniqueValue);
			object=JStringUtils.isNullOrEmpty(actualParams)?actualParams:((object==null?EMPTY:object)+actualParams);
			memcachedService.set(linkedUniqueValue, 60, object);
			return stored;
		}

		@Override
		public String get() {
			String object=(String) memcachedService.get(linkedUniqueValue);
			memcachedService.delete(linkedUniqueValue);
			object=JStringUtils.isNullOrEmpty(actualParams)?(object==null?EMPTY:object):((object==null?EMPTY:object)+actualParams);
			return object;
		}
		
		public String getParameter() {
			return get();
		};
		
	}
	
	private Trace<?> newObject(){
		switch (storeType) {
		case SimpleString:
			return new SimpleStringTrace();
		case LinkedObject:
			return new LinkedObjectTrace();
		default:
			return null;
		}
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
				if(LOGGER.isDebugEnabled()){
					LOGGER.debug(linkedUniqueValue+"--put-->"+actualParams);
				}
				stored=trace.store();
				this.isStored=true;
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
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug(linkedUniqueValue+"-get->"+actualParams);
		}
		
		String object=trace.getParameter();
		
		if(LOGGER.isDebugEnabled()){
			LOGGER.debug(linkedUniqueValue+"-the whole parameter->"+object);
			LOGGER.debug(linkedUniqueValue+"-the whole parameter->"+YouAppMvcUtils.decode(object)); 
		}
		return object;
	}
	
	
	
	/**
	 * generally, the method will be called when then method {@link #store()} returns true.
	 * @return
	 */
	public LinkedRequestResponse next(){
		LinkedRequestResponse multiRequest=new LinkedRequestResponse();
		multiRequest.setNext(true);
		multiRequest.setMessage("Do next.");
		return multiRequest;
	}
	
	/**
	 * generally, the method will be called when then method {@link #store()} returns false.
	 * @return
	 */
	public LinkedRequestResponse terminate(){
		LinkedRequestResponse multiRequest=new LinkedRequestResponse();
		multiRequest.setNext(false);
		multiRequest.setMessage(exception.getMessage());
		return multiRequest;
	}
	
	/**
	 * get value according to he special key
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getParameter(HttpServletRequest request,String key) {
		Map<?,?> map=(Map<?, ?>) YouAppMvcUtils.getAdditionalAttributesInRequestScope(request, LINKED_REQUEST_PARAM_KEY);
		String value=(String) ((map==null?null:map.get(key)));
		return value;
	}
	
	/**
	 * get all parameters of the linked request.
	 * @param request
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Object> getParameters(HttpServletRequest request) {
		return (Map<String,Object>) YouAppMvcUtils.getAdditionalAttributesInRequestScope(request, LINKED_REQUEST_PARAM_KEY);
	}
	
	
	/**
	 * get values according to he special key
	 * @param request
	 * @param key
	 * @return
	 */
	public static String[] getParameterValues(HttpServletRequest request,String key) {
		Map<?,?> map=(Map<?, ?>) YouAppMvcUtils.getAdditionalAttributesInRequestScope(request, LINKED_REQUEST_PARAM_KEY);
		String[] value =(String[]) ((map==null?null:map.get(key)));
		return value;
	}
	
	/**
	 * put linked parameters into request scope with the key : {@value #LINKED_REQUEST_PARAM_KEY}.
	 * @see YouAppMvcUtils#setAdditionalAttributesInRequestScope(HttpServletRequest, String, Object)
	 */
	public void setLinkedParameters(){
		String allParameters=get();
		Map<String,String> params= YouAppMvcUtils.parseQueryString(YouAppMvcUtils.decode(allParameters));
		YouAppMvcUtils.setAdditionalAttributesInRequestScope(request, LINKED_REQUEST_PARAM_KEY, params);
	}
	
	
	

	
}
