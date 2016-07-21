package j.jave.web.htmlclient.form;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

import javax.servlet.http.HttpServletRequest;

public class DefaultServletSessionTokenStorage extends JServiceFactorySupport<DefaultServletSessionTokenStorage>
implements TokenStorageService
{
	
	private static final ThreadLocal<HttpServletRequest> THREAD_LOCAL=new ThreadLocal<HttpServletRequest>();
	
	@Override
	public String getSessionId() {
		return THREAD_LOCAL.get().getSession().getId();
	}
	
	@Override
	public boolean store(FormIdentification formIdentification) {
		THREAD_LOCAL.get().getSession().setAttribute(formIdentification.getFormId(), formIdentification);
		return true;
	}

	@Override
	public FormIdentification getToken(String formId) {
		return (FormIdentification) THREAD_LOCAL.get().getSession().getAttribute(formId);
	}
	
	@Override
	public boolean removeBySessionId(String sessionId) {
		return true;
	}
	
	@Override
	public boolean removeByFormId(String formId) {
		THREAD_LOCAL.get().getSession().removeAttribute(formId);
		return true;
	}
	
	
	@Override
	protected DefaultServletSessionTokenStorage doGetService() {
		return this;
	}
}
