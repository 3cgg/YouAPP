package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.util.List;

/**
 * an asynchronized event call back executing pipe.
 * @author J
 *
 */
public class JEventQueueEventResultCallBackPipe extends JEventQueuePipe {
	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueEventResultCallBackPipe.class.getSimpleName());
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
				final JPersistenceEventAsyncCallbackTask eventAsyncCallbackTask=new JPersistenceEventAsyncCallbackTask(execution);
				execution.setPhase(Phase.EVENT_CALLBACK_ING);
				return eventAsyncCallbackTask;
			}

			@Override
			public void postProcess(JEventExecution execution) {
				execution.setPhase(Phase.EVENT_CALLBACK_DONE);
			}
			
		};
	}
	
	@Override
	protected boolean canProcessing(JEventExecution eventExecution) {
		boolean canProcessing= JCollectionUtils.hasInCollect(eventExecution.getAsyncCallbackChain());
		return canProcessing;
	}
	
	@Override
	protected void prepareProcessing(JEventExecution eventExecution) {
		eventExecution.setPhase(Phase.EVENT_CALLBACK_READY);
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
					asyncCallbacks.get(i).callback((EventExecutionResult) eventExecution.getResult(),eventExecution);
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
