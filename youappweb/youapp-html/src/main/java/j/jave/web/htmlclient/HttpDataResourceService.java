package j.jave.web.htmlclient;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

import java.util.Map;

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
