package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.kernal.jave.support.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.util.AbstractQueue;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class JEventQueueOUTPipe extends JEventQueuePipe {
	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueOUTPipe.class.getName());
		return config;
	}
	
	@Override
	protected JAbstractEventExecutionHandler getHandler() {
		return new JAbstractEventExecutionHandler() {

			@Override
			public boolean isLaterProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				return false;
			}
			
			@Override
			public JPersistenceTask persistenceTask(JEventExecution execution,
					AbstractQueue<JEventExecution> executions) {
				final JPersistenceEventAsyncCallbackTask eventAsyncCallbackTask=new JPersistenceEventAsyncCallbackTask(execution);
				execution.setPhase(Phase.EVENT_CALLBACK_ING);
				return eventAsyncCallbackTask;
			}

			@Override
			public void postProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				execution.setPhase(Phase.EVENT_CALLBACK_DONE);
				handoff(execution);
			}
			
		};
	}
	
	protected void addEventExecution(JEventExecution eventExecution){
		//if need event callback.
		if(JCollectionUtils.hasInCollect(eventExecution.getAsyncCallbackChain())){
			eventExecution.setPhase(Phase.EVENT_CALLBACK_READY);
			execute(eventExecution);
		}
		else{
			waitForGets.put(eventExecution.getEvent().getUnique(), eventExecution);
		}
	}
	
	/**
	 * KEY: UNIQUE VALUE. it's the value of property <code>unique</code> of {@code JAPPEvent}
	 * <p>VALUE:  the event response that means the event is done completely.
	 * <p>about the collection we can consider to store all data in the file system whose active time after executed by thread is long, 
	 * void the OutOfMemory.. 
	 */
	final ConcurrentHashMap<String, JEventExecution> waitForGets=new ConcurrentHashMap<String, JEventExecution>();

	/**
	 * get result, if the result is still not calculated, a exception of <code>JEventExecutionException.EVENT_NOT_COMPLETE</code> thrown.
	 * @param eventUnique
	 * @return
	 * @throws JEventExecutionException
	 */
	Object getEventResult(String eventUnique) throws JEventExecutionException{
		if(waitForGets.containsKey(eventUnique)){
			JEventExecution eventExecution= waitForGets.get(eventUnique);
			waitForGets.remove(eventUnique);
			return eventExecution.getResult();
		}
		throw new JEventExecutionException(JEventExecutionException.EVENT_NOT_COMPLETE,
				"event is still not executed completely! please wait.");
	}
	
	@SuppressWarnings("serial")
	public static class JPersistenceEventAsyncCallbackTask extends JPersistenceTask{
		
		public JPersistenceEventAsyncCallbackTask(
				JEventExecution eventExecution) {
			super(eventExecution);
		}

		@Override
		public Object execute() {
			List<JAsyncCallback> asyncCallbacks=eventExecution.getAsyncCallbackChain();
			if(JCollectionUtils.hasInCollect(asyncCallbacks)){
				for(int i=0;i<asyncCallbacks.size();i++){
					eventExecution.setCurrentCallbackIndex(i);
					asyncCallbacks.get(i).callback((Object[]) eventExecution.getResult(),eventExecution);
				}
			}
			return VOID;
		}
		@Override
		public boolean isVoid() {
			return true;
		}
	}
}
