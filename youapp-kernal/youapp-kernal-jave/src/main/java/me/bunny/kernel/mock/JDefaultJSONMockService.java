package me.bunny.kernel.mock;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.utils.JStringUtils;

public class JDefaultJSONMockService
extends JServiceFactorySupport<JDefaultJSONMockService>
implements JJSONMockService {
	
	private JURIGetDataService uriGetDataService=JServiceHubDelegate.get()
			.getService(this, JDefaultURIGetDataService.class);
	
	private JURIGetDataService getUriGetDataService() {
		return uriGetDataService;
	}
	
	@Override
	public Object mockData(JMockModel mockModel) throws Exception {
		Object data=null;
		String uri=mockModel.getUri();
		if(JStringUtils.isNotNullOrEmpty(uri)){
			data=uriGetDataService.getData(uri);
		}
		else{
			data=mockModel.getData();
		}
		return data;
	}

	@Override
	public boolean accept(JMockModel mockModel) {
		return mockModel.isMock();
	}
	
	@Override
	public JDefaultJSONMockService doGetService() {
		return this;
	}
}
