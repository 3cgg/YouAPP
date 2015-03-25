package j.jave.framework.components.core;

import java.io.Serializable;

public class HTTPReponseJSON implements Serializable {

	private String status;
	
	private String content;
	
	private String template;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
	
	
}
