package j.jave.web.htmlclient;

import java.util.Map;

import me.bunny.kernel._c.service.JService;

public interface DataResourceService extends JService{
	
	public Map<String, Object> data(String dataUrl,RequestContext context);
	
	
}
