package j.jave.kernal.jave.startup;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.startup.eventlistener.JApplicationStartupServicePushCompleteEvent;
import j.jave.kernal.jave.startup.eventlistener.JApplicationStartupServicePushCompleteListener;
import j.jave.kernal.jave.support.JPriorityBlockingQueue;

public class JStartupServiceChainService extends JServiceFactorySupport<JStartupServiceChainService> 
implements JService, JApplicationStartupServicePushCompleteListener{
	
	private JPriorityBlockingQueue<JStartupService> services=new JPriorityBlockingQueue<JStartupService>();

	private static JStartupServiceChainService instance=new JStartupServiceChainService(); 
	
	@Override
	public Object trigger(JApplicationStartupServicePushCompleteEvent event) {
		
		JConfiguration configuration=event.getConfiguration();
		if(configuration==null) configuration=JConfiguration.get();
		for(JStartupService startupService:services){
			try{
//				startupService.startup(configuration);
			}catch(Exception e){
				throw new JInitializationException(e);	
			}
		}
		return true;
	}
	
	public static class StartupOrderedService implements Comparable<StartupOrderedService>
	, JStartupService{
		
		public StartupOrderedService(JStartupService startupService) {
			JOrder orderAnnotation= startupService.getClass().getAnnotation(JOrder.class);
			if(orderAnnotation==null){
				this.order=JOrdered.LOWEST_PRECEDENCE;
			}
			else{
				this.order=orderAnnotation.value();
			}
			this.startupService=startupService;
		}
		
		JStartupService startupService;
		
		Integer order;
		
		@Override
		public int compareTo(StartupOrderedService o) {
			return o.order.compareTo(order);
		}
		
		public void startup(JConfiguration configuration) throws Exception {
//			startupService.startup(configuration);
		}
		
	}
	
	
	
	@Override
	public JStartupServiceChainService getService() {
		return instance;
	}
	
	public void pushStartupService(JStartupService startupService){
		services.add(new StartupOrderedService(startupService));
	}
	
	
	
	
	
}
