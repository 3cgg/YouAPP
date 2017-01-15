package me.bunny.kernel.jave.async;

import me.bunny.kernel.eventdriven.servicehub.JAsyncCallback;
import me.bunny.kernel.eventdriven.servicehub.JEventExecutionException;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;
import me.bunny.kernel.jave.service.JService;

public class JAsyncTaskExecutingService
extends JServiceFactorySupport<JAsyncTaskExecutingService>
implements JService,JAsyncTaskExecutingListener
{
	
	private JAsyncTaskExecutingServicePipeline taskExecutingServicePipeline=new JAsyncTaskExecutingServicePipeline("-ASYNC-TASK-EXECUTING");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object trigger(JAsyncTaskExecutingEvent event) {
		JAsyncExecutor executor=event.getExecutor();
		try {
			return executor.execute(event.getData());
		} catch (Exception e) {
			throw new JEventExecutionException(e);
		}
	}
	
	@Override
	protected JAsyncTaskExecutingService doGetService() {
		return this;
	}

	public <T> void addAsyncTask(T data,JAsyncExecutor<T> asyncExecutor,JAsyncCallback asyncCallback){
		JAsyncTaskExecutingEvent event =new JAsyncTaskExecutingEvent(this, data);
		event.setExecutor(asyncExecutor);
		event.addAsyncCallback(asyncCallback==null?JYouAPPEvent.NOTHING:asyncCallback);
		taskExecutingServicePipeline.addAPPEvent(event);
	}
	
	public <T> void addAsyncTask(T data,JAsyncExecutor<T> asyncExecutor){
		addAsyncTask(data, asyncExecutor,null);
	}
	
	public <T> void addAsyncTask(JAsyncExecutor<T> asyncExecutor){
		addAsyncTask(null, asyncExecutor,null);
	}
	
	
	public <T> void addAsyncTask(JAsyncExecutor<T> asyncExecutor,JAsyncCallback asyncCallback){
		addAsyncTask(null, asyncExecutor,asyncCallback);
	}
	
}
