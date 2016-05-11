package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JServiceAddNotifyListener;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.util.Collections;
import java.util.List;

public class JServiceMonitor extends JServiceFactorySupport<JServiceMonitorService>
implements JService, JServiceAddNotifyListener, JServiceMonitorService{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JServiceMonitor.class); 
	
	private JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	@Override
	public Object trigger(JServiceAddNotifyEvent event) {
		LOGGER.info("scanning ::::: "+event.getServiceClass().getName());
		return true;
	}
	
	@Override
	public JServiceMonitorService getService() {
		return this;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JServiceHubMeta getServiceHubMeta(){
		return serviceHubDelegate.addImmediateEvent(new JServiceHubMonitorEvent(this),JServiceHubMeta.class);
	}
	 
	@Override
	@SuppressWarnings("unchecked")
	public List<JServiceRuntimeMeta> getServiceRuntimeMetas(Class<? extends JService>... serviceClasses){
		List<JServiceRuntimeMeta> serviceRuntimeMetas=
				serviceHubDelegate.addImmediateEvent(new JServiceMonitorEvent(this,serviceClasses),List.class);
		return serviceRuntimeMetas==null?Collections.EMPTY_LIST:serviceRuntimeMetas;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public JServiceRuntimeMeta getServiceRuntimeMeta(Class<? extends JService> serviceClass){
		List<JServiceRuntimeMeta> serviceRuntimeMetas=
				serviceHubDelegate.addImmediateEvent(new JServiceMonitorEvent(this,serviceClass),List.class);
		if(JCollectionUtils.hasInCollect(serviceRuntimeMetas)){
			return serviceRuntimeMetas.get(0);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
