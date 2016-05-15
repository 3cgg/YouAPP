package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;

/**
 * persist the event result if possible.
 * @author J
 *
 */
public class JEventQueueEventResultPersistencePipe extends JEventQueuePipe {

	private JAsyncEventResultStorageService eventResultRepo=JAsyncEventResultStorageServiceUtil.get();
	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueEventResultPersistencePipe.class.getSimpleName());
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
				eventResultRepo.putEventResult(execution);
				execution.setPhase(Phase.EVENT_PERSITENCE_ING);
				return null;
			}

			@Override
			public void postProcess(JEventExecution execution) {
				execution.setPhase(Phase.EVENT_PERSITENCE_DONE);
			}
			
		};
	}
	
	@Override
	protected boolean canProcessing(JEventExecution eventExecution) {
		return eventExecution.getEvent().isGetResultLater();
	}
	
	@Override
	protected void prepareProcessing(JEventExecution eventExecution) {
		eventExecution.setPhase(Phase.EVENT_PERSITENCE_READY);
	}
	
}
