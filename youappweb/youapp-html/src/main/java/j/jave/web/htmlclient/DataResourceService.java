package j.jave.web.htmlclient;

import j.jave.kernal.jave.service.JService;

import java.util.Map;

public interface DataResourceService extends JService{
	
	public Map<String, Object> data(String dataUrl,RequestContext context);
	
	
}
