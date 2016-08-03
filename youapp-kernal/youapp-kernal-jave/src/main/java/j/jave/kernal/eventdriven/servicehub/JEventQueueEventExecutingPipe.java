package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;

/**
 * retrieve the {@link JYouAPPEvent} driven by the {@link JServiceEventProcessor},
 * default the event pipe is the first element in the {@link JEventQueuePipeline}.
 * the pipe does not support {@link #addEventExecution(JEventExecution)}.
 * @author J
 */
public class JEventQueueEventExecutingPipe extends JEventQueuePipe{

	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueEventExecutingPipe.class.getSimpleName());
		return config;
	}
	
	@Override
	protected JAbstractEventExecutionHandler getHandler() {
		return new JAbstractEventExecutionHandler() {

			@Override
			public boolean isLaterProcess(JEventExecution execution) {
				return false;
			}

			@Override
			public JPersistenceTask persistenceTask(JEventExecution execution) {
				final JPersistenceTask persitenceTask=new JPersistenceExecuteEventOnListenerTask(execution);
				return persitenceTask;
			}
			
			@Override
			public void postProcess(JEventExecution execution) {
			}
		};
	}
	
	protected void addAPPEvent(JYouAPPEvent<? > appEvent){
		JEventExecution eventInfo=new JEventExecution();
		eventInfo.setEvent(appEvent);
		eventInfo.addAsyncCallbacks(appEvent.getBackendAsyncCallbacks());
		eventInfo.setProcessed(false);
		eventInfo.setPhase(Phase.EVENT_CONSUME_READY);
		addEventExecution(eventInfo);
	}

	@SuppressWarnings("serial")
	public static class JPersistenceExecuteEventOnListenerTask extends JPersistenceTask{
		
		public JPersistenceExecuteEventOnListenerTask(JEventExecution eventExecution){
			super(eventExecution);
		}
		
		public Object execute() {
			Object object= JServiceHub.get().executeEventOnListener(eventExecution.getEvent());
			// notify the event is processed completely.
			JServiceHubDelegate.get().propagateEventRequestEndNotifyEvent(eventExecution.getEvent());
			return object;
		}
		
		@Override
		public boolean isVoid() {
			return false;
		}
		
	}
	
}
