package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JCacheService;
import j.jave.kernal.jave.service.JService;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class JSyncMonitorRegisterService
extends JServiceFactorySupport<JSyncMonitorRegisterService>
implements JService,JSyncMonitorWakeupListener
{
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private ConcurrentHashMap<String, TemporaySyncMonitor> 
	syncMonitors=new ConcurrentHashMap<String, JSyncMonitorRegisterService.TemporaySyncMonitor>();
	
	private static class TemporaySyncMonitor{
		
		private String unique;
		
		private int count;
		
		private JSyncMonitor syncMonitor;
		
		private Date start;
		
		private JCacheService cacheService;
		
	}
	
	public void sync(JSyncMonitor monitor,JCacheService cacheService){
		sync(monitor,new JSyncConfig(), cacheService);
	}
	
	public void sync(JSyncMonitor monitor,JSyncConfig syncConfig){
		sync(monitor,syncConfig, null);
	}
	
	public void sync(JSyncMonitor monitor, JSyncConfig syncConfig,JCacheService cacheService ){
		TemporaySyncMonitor temporaySyncMonitor=new TemporaySyncMonitor();
		temporaySyncMonitor.unique=monitor.unique();
		temporaySyncMonitor.syncMonitor=monitor;
		temporaySyncMonitor.start=new Date();
		temporaySyncMonitor.cacheService=cacheService;
		syncMonitors.put(monitor.unique(), temporaySyncMonitor);
		synchronized (monitor) {
        	try {
        		monitor.wait();
        		System.out.println("WAKEUP...");
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	@Override
	public Object trigger(JSyncMonitorWakeupEvent event) {
		String unique=event.getSyncMonitorUnique();
		TemporaySyncMonitor temporaySyncMonitor= syncMonitors.get(unique);

		if(temporaySyncMonitor==null){
			event.setCount(event.getCount()+1);
			JServiceHubDelegate.get().addDelayEvent(event);
			return false;
		}
		
		if(temporaySyncMonitor.cacheService!=null){
			temporaySyncMonitor.cacheService.putNeverExpired(unique, event.getData());
		}
		if(temporaySyncMonitor!=null){
			JSyncMonitor syncMonitor=temporaySyncMonitor.syncMonitor;
			synchronized (syncMonitor) {
				syncMonitor.notifyAll();
			}
		}
		return true;
	}


	@Override
	public JSyncMonitorRegisterService getService() {
		return this;
	}




}
