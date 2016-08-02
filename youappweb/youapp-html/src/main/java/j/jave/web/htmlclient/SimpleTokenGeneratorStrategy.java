package j.jave.web.htmlclient;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.web.htmlclient.form.FormIdentification;
import j.jave.web.htmlclient.form.VoidDuplicateSubmitService;
import j.jave.web.htmlclient.request.RequestHtml;

public class SimpleTokenGeneratorStrategy implements TokenGeneratorStrategy {

	private VoidDuplicateSubmitService voidDuplicateSubmitService=
			JServiceHubDelegate.get().getService(this, VoidDuplicateSubmitService.class);
	
	@Override
	public FormIdentification newFormIdentification(RequestHtml requestHtml) {
		String html=requestHtml.getHtmlUrl();
		if(html.endsWith("-add.html")||html.endsWith("-edit.html")
				||html.endsWith("-create.html")||html.endsWith("-modify.html")
				){
			return voidDuplicateSubmitService.newFormIdentification();
		}
		return null;
	}

}
