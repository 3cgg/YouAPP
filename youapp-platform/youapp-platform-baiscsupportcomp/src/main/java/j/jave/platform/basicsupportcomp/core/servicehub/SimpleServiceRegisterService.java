package j.jave.platform.basicsupportcomp.core.servicehub;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.jave.service.JService;

import org.springframework.stereotype.Service;

@Service
public class SimpleServiceRegisterService extends SpringServiceFactorySupport<SimpleServiceRegisterService> implements JService {

	@Override
	public SimpleServiceRegisterService getService() {
		return this;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		JServiceFactoryManager.get().registerAllServices();
	}
	
}
