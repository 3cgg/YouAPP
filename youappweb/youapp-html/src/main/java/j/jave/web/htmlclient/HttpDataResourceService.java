package j.jave.web.htmlclient;

import java.util.Map;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class HttpDataResourceService
extends JServiceFactorySupport<HttpDataResourceService>
implements DataResourceService {

	
	
	@Override
	public Map<String, Object> data(String dataUrl, RequestContext context) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	
	
	
	

	@Override
	protected HttpDataResourceService doGetService() {
		return this;
	}
	
}
