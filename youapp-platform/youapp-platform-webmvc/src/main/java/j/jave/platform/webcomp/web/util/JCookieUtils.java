package j.jave.platform.webcomp.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JCookieUtils {

	public static String getValue(HttpServletRequest request, String name){
		Cookie cookie=getCookie(request, name);
		if(cookie!=null){
			return cookie.getValue();
		}
		return null;
	}
	
	public static Cookie getCookie(HttpServletRequest request, String name) {
	    Cookie cookies[] = request.getCookies();
	    if (cookies == null || name == null || name.length() == 0) {
	      return null;
	    }
	    for (int i = 0; i < cookies.length; i++) {
	      if (name.equals(cookies[i].getName())
	          //&& request.getServerName().equals(cookies[i].getDomain())
	          ) {
	        return cookies[i];
	      }
	    }
	    return null;
	  }

	  public static void deleteCookie(HttpServletRequest request,
	      HttpServletResponse response, Cookie cookie) {
	    if (cookie != null) {
	      cookie.setPath(getPath(request));
	      cookie.setValue("");
	      cookie.setMaxAge(0);
	      response.addCookie(cookie);
	    }
	  }

	  public static void setCookie(HttpServletRequest request,
	      HttpServletResponse response, String name, String value) {
	    setCookie(request, response, name, value, 60);
	  }

	  public static void setCookie(HttpServletRequest request,
	      HttpServletResponse response, String name, String value, int maxAge) {
	    Cookie cookie = new Cookie(name, value == null ? "" : value);
	    cookie.setMaxAge(maxAge);
	    cookie.setPath(getPath(request));
	    response.addCookie(cookie);
	  }

	  private static String getPath(HttpServletRequest request) {
	    String path = request.getContextPath();
	    return (path == null || path.length()==0) ? "/" : path;
	  }

}
