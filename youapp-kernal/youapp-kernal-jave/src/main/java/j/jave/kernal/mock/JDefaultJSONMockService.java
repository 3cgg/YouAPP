package j.jave.kernal.mock;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.utils.JStringUtils;

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
