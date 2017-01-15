package j.jave.web.htmlclient;

import j.jave.web.htmlclient.form.FormIdentification;
import j.jave.web.htmlclient.request.RequestHtml;
import me.bunny.kernel._c.service.JService;

public interface TokenGeneratorStrategy extends JService {

	public FormIdentification newFormIdentification(RequestHtml requestHtml);
	
}
