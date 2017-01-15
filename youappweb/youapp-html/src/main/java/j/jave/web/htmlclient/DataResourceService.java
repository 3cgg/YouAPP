package j.jave.web.htmlclient;

import java.util.Map;

import me.bunny.kernel.jave.service.JService;

public interface DataResourceService extends JService{
	
	public Map<String, Object> data(String dataUrl,RequestContext context);
	
	
}
