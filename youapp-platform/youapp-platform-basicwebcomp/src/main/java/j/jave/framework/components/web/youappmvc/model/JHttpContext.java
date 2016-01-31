package j.jave.framework.components.web.youappmvc.model;

import j.jave.framework.commons.utils.JCollectionUtils;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.login.subhub.SessionUser;
import j.jave.framework.components.web.util.JCookieUtils;
import j.jave.framework.components.web.youappmvc.action.ActionExecutor;
import j.jave.framework.components.web.youappmvc.multi.platform.support.JLinkedRequestSupport;
import j.jave.framework.components.web.youappmvc.utils.JHttpUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP CONTEXT Wrapper , with which ACTION EXECUTOR can perform operation.
 * The class detect if the attribute {@link JLinkedRequestSupport#LINKED_REQUEST_PARAM_KEY} indicates linked request is existing , if exists extract them into {@link #parameters}
 * <p><strong>note that properties below is mandatory :</strong>
 * <p> {@link #ticket}
 * <p> {@link #user}
 * <p> {@link #targetPath}
 * @author J
 * @see ActionExecutor
 */
public class JHttpContext implements Serializable {

	private static final long serialVersionUID = -3949287782520790723L;

	/**
	 * ticket to indicate the unique request to apart from other users.
	 * <strong>mandatory</strong>
	 */
	private String ticket;

	/**
	 * user information , generally see {@link SessionUser}
	 * <strong>mandatory</strong>
	 */
	private SessionUser user;
	
	/**
	 * HTTP Servlet Request. 
	 * <strong>optional</strong>
	 */
	private final transient  HttpServletRequest request;

	/**
	 * HTTP Servlet Response. 
	 * <strong>optional</strong>
	 */
	private final transient HttpServletResponse response;

	/**
	 * HTTP Servlet Request Parameter.  may processed after file distribute service. 
	 * <strong>optional</strong>
	 */
	private transient Map<String, Object> parameters = new HashMap<String, Object>();
	
	/**
	 * can resolve the path to an object in which inner method is . 
	 * like "/login.loginaction/toLogin" , the pattern like "/bean-name/method(with no any arguments)"
	 * <strong>mandatory</strong>
	 */
	private String targetPath;
	
	/**
	 * the key is used to for whose parameters that are added in request scope by framework later.
	 * {@link HttpServletRequest#setAttribute(String, Object)}
	 * as key {@value #ADDITIONAL_PARAM_KEY}
	 */
	public static final String ADDITIONAL_PARAM_KEY="j.jave.framework.components.web.additional.param";
	
	/**
	 * true if the context is created by the last request of the linked request. 
	 * i.e. {@value #LINKED_REQUEST_PARAM_KEY} exists in the request scope.
	 */
	private transient volatile boolean linked=false; 
	
	public JHttpContext(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
		init();
	}
	
	public JHttpContext(){
		this(null, null);
	}
	
	private void init(){
		boolean parse=false;
		// process request with context type : multipart/form-data
		if(request!=null&&JHttpUtils.isFileContextType(request)){
			Map<String, Object> parameterValues=JHttpUtils.doWithRequestParameterWithFileAttached(request);
			this.parameters.putAll(parameterValues);
			parse=true;
		}

		//initialize linked parameters from the request scope.
		// avoid process those requests of FORWARD, INCLUDE ， specially aware of  FORWARD
		if(request!=null&&JHttpUtils.isRequestTypeAndGET(request)&&JLinkedRequestSupport.isLinked(request)){
			linked=true;
			Map<String,Object> linkedParameters=(Map<String, Object>) JLinkedRequestSupport.getParameters(request);
			if(JCollectionUtils.hasInMap(linkedParameters)){
				for (Iterator<Entry<String, Object>> iterator = linkedParameters.entrySet().iterator(); iterator.hasNext();) {
					Entry<String, Object> entry =  iterator.next();
					if(JStringUtils.isNotNullOrEmpty(entry.getKey())){
						this.parameters.put(entry.getKey(), entry.getValue());
					}
				}
			}
			parse=true;
		}
		
		
		if(request!=null&&!parse){
			this.parameters.putAll(JHttpUtils.parseRequestParameters(request));
			parse=true;
		}
		
	}
	
	public void setAttribute(String key, Object obj) {
		request.setAttribute(key, obj);
	}
	
	public String getParameter(String key) {
		String value = (String) parameters.get(key);
		return value;
	}
	
	public String[] getParameterValues(String key) {
		String[] value =  (String[]) parameters.get(key);
		return value;
	}
	
	public Object getParameterValue(String key) {
		return parameters.get(key);
	}
	

	public Cookie getCookie(String name) {
		return JCookieUtils.getCookie(request, name);
	}

	public void deleteCookie(Cookie cookie) {
		JCookieUtils.deleteCookie(request, response, cookie);
	}
	
	public void deleteCookie(String name){
		deleteCookie(getCookie(name));
	}
	

	public void setCookie(String name, String value) {
		JCookieUtils.setCookie(request, response, name, value);
	}

	public void setCookie(String name, String value, int maxAge) {
		JCookieUtils.setCookie(request, response, name, value, maxAge);
	}

	/**
	 * {@link #parameters}, which store query string parameter or any other added by program later.
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	/**
	 * the method is only used to initialize parameters for testing without HTTP context.
	 * @param parameters
	 */
	public void initParametersWithoutHTTP(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * {@link JHttpContext#user}
	 * @return generally <code>SessionUser</code> returned.
	 */
	public SessionUser getUser() {
		return user;
	}

	public void setUser(SessionUser user) {
		this.user = user;
	}
	
	public String getIP(){
		return JHttpUtils.getIP(request);
	}
	
	public String getClient(){
		return JHttpUtils.getClient(request);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
	
	/**
	 * see {@link #linked}
	 * @return
	 */
	public boolean isLinked() {
		return linked;
	}
	
}
