package j.jave.platform.webcomp.web.form;

import j.jave.kernal.jave.service.JService;

public interface TokenStorageService extends JService {

	String getSessionId();
	
	boolean store(FormIdentification formIdentification);
	
	FormIdentification getToken(String formId);
	
	boolean removeBySessionId(String sessionId);
	
	boolean removeByFormId(String formId);
	
}
