package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class JEventQueueEventResultGettingPipe extends JEventQueuePipe{
	
	
	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueEventResultGettingPipe.class.getName());
		return config;
	}
	
	@Override
	protected JAbstractEventExecutionHandler getHandler() {
		return new JAbstractEventExecutionHandler() {
			@Override
			public boolean isLaterProcess(JEventExecution execution) {
				try {
					FutureTask<?> futureTask=(FutureTask<?>) execution.getRunnable();
					if(futureTask!=null){
						execution.setPhase(Phase.EVENT_RESULT_GET_ING);
						Object object= futureTask.get(1, TimeUnit.SECONDS);
						execution.setResult(object);
						execution.setProcessed(true);
						execution.setPhase(Phase.EVENT_RESULT_GET_DONE);
					}
					return false;
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					execution.setPhase(Phase.EVENT_RESULT_GET_READY);
					// put to last, give another chance to get result.
					return true;
				}
			}

			@Override
			public JPersistenceTask persistenceTask(JEventExecution execution) {
				LOGGER.debug("event is processed completely!");
				return null;
			}

			@Override
			public void postProcess(JEventExecution execution) {
			}
			
		};
	}
	
	@Override
	protected void prepareProcessing(JEventExecution eventExecution) {
		eventExecution.setPhase(Phase.EVENT_RESULT_GET_READY);
	}
	
}
