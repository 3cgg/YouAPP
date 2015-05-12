package j.jave.framework.components.web.action;

import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.view.SessionUser;
import j.jave.framework.components.web.utils.CookieUtils;
import j.jave.framework.components.web.utils.HTTPUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP CONTEXT Wrapper , with which ACTION EXECUTOR can perform operation.
 * <p><strong>note that properties below is mandatory :</strong>
 * <p> {@link #ticket}
 * <p> {@link #user}
 * <p> {@link #targetPath}
 * @author J
 * @see ActionExecutor
 */
public class HTTPContext implements Serializable {

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
	private User user;
	
	/**
	 * HTTP Servlet Request. 
	 * <strong>optional</strong>
	 */
	private transient  HttpServletRequest request;

	/**
	 * HTTP Servlet Response. 
	 * <strong>optional</strong>
	 */
	private transient HttpServletResponse response;

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
	
	
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setAttribute(String key, Object obj) {
		request.setAttribute(key, obj);
	}

	public String getParameter(String key) {
		String value = null;
		if ((value = request.getParameter(key)) == null) {
			value = (String) parameters.get(key);
		}
		return value;
	}
	
	public String[] getParameterValues(String key) {
		String[] value = null;
		if ((value = request.getParameterValues(key)) == null) {
			value = (String[]) parameters.get(key);
		}
		return value;
	}

	/**
	 * @param parameters
	 *            the parameters to set
	 */
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public Cookie getCookie(String name) {
		return CookieUtils.getCookie(request, name);
	}

	public void deleteCookie(Cookie cookie) {
		CookieUtils.deleteCookie(request, response, cookie);
	}
	
	public void deleteCookie(String name){
		deleteCookie(getCookie(name));
	}
	

	public void setCookie(String name, String value) {
		CookieUtils.setCookie(request, response, name, value);
	}

	public void setCookie(String name, String value, int maxAge) {
		CookieUtils.setCookie(request, response, name, value, maxAge);
	}

	/**
	 * @return the parameters
	 */
	public Map<String, Object> getParameters() {
		return parameters;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * {@link HTTPContext#user}
	 * @return generally <code>SessionUser</code> returned.
	 */
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getIP(){
		return HTTPUtils.getIP(request);
	}
	
	public String getClient(){
		return HTTPUtils.getClient(request);
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
	
	
	
}
