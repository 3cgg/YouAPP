package j.jave.web.htmlclient.request;

import me.bunny.kernel.jave.model.JModel;

/**
 * 
 * @author JIAZJ
 *
 */
public class FormIdentificationVO implements JModel{

	private String sessionId;
	
	private String formId;
	
	private String token;

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
