package j.jave.kernal.jave.sync;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JCacheService;
import j.jave.kernal.jave.service.JService;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class JSyncMonitorRegisterService
extends JServiceFactorySupport<JSyncMonitorRegisterService>
implements JService,JSyncMonitorWakeupListener
{
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private ConcurrentHashMap<String, TemporaySyncMonitor> 
	syncMonitors=new ConcurrentHashMap<String, JSyncMonitorRegisterService.TemporaySyncMonitor>();
	
	@SuppressWarnings("unused")
	private static class TemporaySyncMonitor{

		private String unique;
		
		private int count;
		
		private JSyncMonitor syncMonitor;
		
		private Date start;
		
		private JCacheService cacheService;
		
		/**
		 * the thread monitoring the {@link #syncMonitor} 
		 */
		private Thread thread;
		
	}
	
	private int defaultWaitTime=JConfiguration.get().getInt(
			JProperties.SERVICE_CHANNEL_DATAE_XCHANGE_TIMEOUT, 10)*1000;
	
	public void sync(JSyncMonitor monitor,JCacheService cacheService){
		sync(monitor,new JSyncConfig(defaultWaitTime), cacheService);
	}
	
	public void sync(JSyncMonitor monitor,JSyncConfig syncConfig){
		sync(monitor,syncConfig, null);
	}
	
	public void sync(JSyncMonitor monitor){
		sync(monitor, new JSyncConfig(defaultWaitTime));
	}
	
	private ReentrantLock lock=new ReentrantLock();
	
	public void sync(JSyncMonitor monitor, JSyncConfig syncConfig,JCacheService cacheService ){
		
		try{
			lock.lockInterruptibly();
			String unique=monitor.unique();
			if(syncMonitors.containsKey(unique)){
				throw new IllegalStateException("the monitor["+unique+"] is held by other threads.");
			}
			TemporaySyncMonitor temporaySyncMonitor=new TemporaySyncMonitor();
			temporaySyncMonitor.unique=unique;
			temporaySyncMonitor.syncMonitor=monitor;
			temporaySyncMonitor.start=new Date();
			temporaySyncMonitor.cacheService=cacheService;
			temporaySyncMonitor.thread=Thread.currentThread();
			syncMonitors.put(monitor.unique(), temporaySyncMonitor);
		}catch(InterruptedException e){
			throw new IllegalStateException(e);
		}finally{
			if(lock.isHeldByCurrentThread()){
				lock.unlock();
			}
		}
		synchronized (monitor) {
        	try {
        		LOGGER.info("thread ["+Thread.currentThread().getName()
        				+"] ready to wait any time, monitoring on "+monitor.unique());
        		int timeout=syncConfig.getWaitTime()<1?defaultWaitTime:syncConfig.getWaitTime();
        		monitor.wait(timeout);
        		LOGGER.info("thread ["+Thread.currentThread().getName()
        				+"] already wake up by other thread , monitoring on "+monitor.unique());
			} catch (InterruptedException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}

	@Override
	public Object trigger(JSyncMonitorWakeupEvent event) {
		String unique=event.getSyncMonitorUnique();
		TemporaySyncMonitor temporaySyncMonitor= syncMonitors.get(unique);

		if(temporaySyncMonitor==null){  // attempt to wake up later.
			int count=event.getCount();
			if(count>10){ //ignore
				return false;
			}
			event.setCount(count+1);
			syncEventQueuePipeline.addAPPEvent(event);
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

	private JSyncEventQueuePipeline syncEventQueuePipeline=new JSyncEventQueuePipeline("-SYNC-MONITOR");

	/**
	 * asynchronize
	 * @param event
	 */
	public void wakeup(JSyncMonitorWakeupEvent event){
		syncEventQueuePipeline.addAPPEvent(event);
	}
	

}
