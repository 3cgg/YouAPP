package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.kernal.eventdriven.servicehub.JQueueDistributeProcessor.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.util.List;

public class JEventQueueEventResultCallBackAndGetLaterPipe extends JEventQueuePipe {
	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueEventResultCallBackAndGetLaterPipe.class.getName());
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
		if(eventExecution.getEvent().isGetResultLater()){
			eventResultRepo.putEventResult(eventExecution);
		}
		eventExecution.setPhase(Phase.EVENT_CALLBACK_READY);
	}
	
//	protected void addEventExecution(JEventExecution eventExecution){
//		//if need event callback.
//		if(JCollectionUtils.hasInCollect(eventExecution.getAsyncCallbackChain())){
//			eventExecution.setPhase(Phase.EVENT_CALLBACK_READY);
//			execute(eventExecution);
//		}
//		else{
//			waitForGets.put(eventExecution.getEvent().getUnique(), eventExecution);
//		}
//	}
	
	private JAsyncEventResultRepoService eventResultRepo=JAsyncEventResultRepoServiceUtil.get();
	
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
