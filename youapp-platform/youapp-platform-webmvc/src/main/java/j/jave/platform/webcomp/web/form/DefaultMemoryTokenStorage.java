package j.jave.platform.webcomp.web.form;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedEvent;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceHubInitializedListener;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultMemoryTokenStorage extends JServiceFactorySupport<DefaultMemoryTokenStorage>
implements TokenStorageService , JServiceHubInitializedListener
{

	private JDefaultHashCacheService defaultHashCacheService
	=JServiceHubDelegate.get().getService(this, JDefaultHashCacheService.class);
	
	private String sessionId=JUniqueUtils.unique();
	
	@Override
	public Object trigger(JServiceHubInitializedEvent event) {
		defaultHashCacheService.putNeverExpired(sessionId, new ConcurrentHashMap<String,FormIdentification>());
		return true;
	}
	
	@Override
	public String getSessionId() {
		return sessionId;
	}

	@SuppressWarnings("unchecked")
	private Map<String,FormIdentification> getInner(){
		return (Map<String, FormIdentification>) defaultHashCacheService.get(sessionId);
	}
	
	
	@Override
	public boolean store(FormIdentification formIdentification) {
		getInner().put(formIdentification.getFormId(), formIdentification);
		return true;
	}

	@Override
	public FormIdentification getToken(String formId) {
		return (FormIdentification) getInner().get(formId);
	}
	
	@Override
	public boolean removeBySessionId(String sessionId) {
		getInner().clear();
		return true;
	}
	
	@Override
	public boolean removeByFormId(String formId) {
		getInner().remove(formId);
		return true;
	}
	
	@Override
	protected DefaultMemoryTokenStorage doGetService() {
		return this;
	}
}
