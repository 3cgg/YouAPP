package j.jave.kernal.mock;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.http.JHttpBase;
import j.jave.kernal.http.JHttpFactoryProvider;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JURIUtils;

import java.util.Map;

public class JDefaultURIGetDataService 
extends JServiceFactorySupport<JDefaultURIGetDataService>
implements JURIGetDataService {

	private JHttpBase<?> HTTP_GET=JHttpFactoryProvider.getHttpFactory(JConfiguration.get())
			.getHttpGet();
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getData(String uri, Map<String, Object>... params) throws Exception {
		Object data=null;
		if(JStringUtils.isNotNullOrEmpty(uri)){
			if(JURIUtils.isHttpProtocol(uri)){
				data=(String) HTTP_GET.execute(uri);
			}
			data=new String(JURIUtils.getBytes(uri),"utf-8");
		}
		return data;
	}
	
}
