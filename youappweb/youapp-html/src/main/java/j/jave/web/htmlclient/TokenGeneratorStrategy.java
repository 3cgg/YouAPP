package j.jave.web.htmlclient;

import j.jave.kernal.jave.service.JService;
import j.jave.web.htmlclient.form.FormIdentification;
import j.jave.web.htmlclient.request.RequestHtml;

public interface TokenGeneratorStrategy extends JService {

	public FormIdentification newFormIdentification(RequestHtml requestHtml);
	
}
