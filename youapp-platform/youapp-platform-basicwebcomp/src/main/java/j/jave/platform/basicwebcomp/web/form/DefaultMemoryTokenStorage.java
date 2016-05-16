package j.jave.platform.basicwebcomp.web.form;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support.JDefaultHashCacheService;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultMemoryTokenStorage extends JServiceFactorySupport<DefaultMemoryTokenStorage>
implements TokenStorageService
{

	private JDefaultHashCacheService defaultHashCacheService
	=JServiceHubDelegate.get().getService(this, JDefaultHashCacheService.class);
	
	private String sessionId=JUniqueUtils.unique();
	{
		defaultHashCacheService.putNeverExpired(sessionId, new ConcurrentHashMap<String,FormIdentification>());
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
	
}
