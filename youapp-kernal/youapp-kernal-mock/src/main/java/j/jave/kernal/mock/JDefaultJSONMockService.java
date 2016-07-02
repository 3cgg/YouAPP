package j.jave.kernal.mock;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.http.JHttpBase;
import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JURIUtils;

public class JDefaultJSONMockService
extends JServiceFactorySupport<JDefaultJSONMockService>
implements JJSONMockService {
	
	private JHttpBase<?> HTTP_GET=JHttpFactoryProvider.getHttpFactory(JConfiguration.get())
			.getHttpGet();
	
	@Override
	public String mockData(JMockModel mockModel) throws Exception {
		String data=null;
		String uri=mockModel.getUri();
		if(JStringUtils.isNotNullOrEmpty(uri)){
			if(JURIUtils.isHttpProtocol(uri)){
				data=(String) HTTP_GET.execute(uri);
			}
			data=new String(JURIUtils.getBytes(uri),"utf-8");
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
	public JDefaultJSONMockService getService() {
		return this;
	}
}
