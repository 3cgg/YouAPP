package j.jave.web.htmlclient.form;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class DefaultServletSessionTokenStorage extends JServiceFactorySupport<DefaultServletSessionTokenStorage>
implements TokenStorageService
{

	private final static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal<HttpServletRequest>();
	
	private final String tokenMap = "_tokenMap";
	
	private final int count=100;
	
	public static void set(HttpServletRequest servletRequest){
		threadLocal.set(servletRequest);
	}
	
	@Override
	public String getSessionId() {
		HttpServletRequest request = (HttpServletRequest)threadLocal.get();
//		HttpSession session = request.getSession();
//		String sessionId = "";
//		synchronized (session) {
//			sessionId = request.getSession().getId();
//		}
		return request.getSession().getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean store(FormIdentification formIdentification) {
		try {
			HttpServletRequest request = (HttpServletRequest)threadLocal.get();
			HttpSession session = request.getSession();
			synchronized (session) {
				LinkedHashMap<String,FormIdentification> map = (LinkedHashMap<String,FormIdentification>)session.getAttribute(tokenMap);
				if(map == null){
					map = new LinkedHashMap<String,FormIdentification>();
					session.setAttribute(tokenMap, map);
				}
				
				if(map.size()>count){
//					throw new TokenMaxException("token exceeds the max count.");
					Iterator<Entry<String,FormIdentification>> iterator= map.entrySet().iterator();
					iterator.next();
					iterator.remove();
				}
				map.put(formIdentification.getFormId(), formIdentification);
			}
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public FormIdentification getToken(String formId) {
		HttpServletRequest request = (HttpServletRequest)threadLocal.get();
		HttpSession session = request.getSession();
		FormIdentification formIdentification = null;
		synchronized (session) {
			Map<String,FormIdentification> map = (Map<String,FormIdentification>)session.getAttribute(tokenMap);
			formIdentification = map.get(formId);
		}
		return formIdentification;
	}

	@Override
	public boolean removeBySessionId(String sessionId) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean removeByFormId(String formId) {
		try {
			HttpServletRequest request = (HttpServletRequest)threadLocal.get();
			HttpSession session = request.getSession();
			synchronized (session) {
				Map<String,FormIdentification> map = (Map<String,FormIdentification>)session.getAttribute(tokenMap);
				map.remove(formId);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	
	@Override
	protected DefaultServletSessionTokenStorage doGetService() {
		return this;
	}
}
