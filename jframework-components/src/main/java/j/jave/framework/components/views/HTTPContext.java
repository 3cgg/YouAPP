package j.jave.framework.components.views;

import j.jave.framework.components.login.model.User;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HTTPContext implements Serializable {

	private String ticket;

	private User user;
	
	private transient  HttpServletRequest request;

	private transient HttpServletResponse response;

	private transient Map<String, Object> parameters = new HashMap<String, Object>();

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
	
	
	
}
