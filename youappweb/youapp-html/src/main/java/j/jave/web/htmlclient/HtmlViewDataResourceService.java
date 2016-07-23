package j.jave.web.htmlclient;

import j.jave.web.htmlclient.request.RequestHtml;

import java.util.HashMap;
import java.util.Map;

public class HtmlViewDataResourceService {

	private static final Map<String, Object> M=new HashMap<String, Object>();
	
	public Map<String, Object> data(RequestHtml requestHtml) {
		return M;
	}
	
}
