package j.jave.kernal.jave.sync;

import j.jave.kernal.eventdriven.servicehub.JEventExecutionException;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

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

	public <T> void addAsyncTask(T data,JAsyncExecutor<T> asyncExecutor){
		JAsyncTaskExecutingEvent event =new JAsyncTaskExecutingEvent(this, data);
		event.setExecutor(asyncExecutor);
		taskExecutingServicePipeline.addAPPEvent(event);
	}
	
	public <T> void addAsyncTask(JAsyncExecutor<T> asyncExecutor){
		addAsyncTask(null, asyncExecutor);
	}
	
	
}
