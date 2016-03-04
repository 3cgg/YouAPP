package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecution.Phase;
import j.jave.kernal.jave.support.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;

import java.util.AbstractQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class JEventQueueProcessingPipe extends JEventQueuePipe{
	
	
	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueProcessingPipe.class.getName());
		return config;
	}
	
	@Override
	protected JAbstractEventExecutionHandler getHandler() {
		return new JAbstractEventExecutionHandler() {
			@Override
			public boolean isLaterProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
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
					return true;
				}
			}

			@Override
			public JPersistenceTask persistenceTask(JEventExecution execution,
					AbstractQueue<JEventExecution> executions) {
				LOGGER.debug("event is processed completely!");
				return null;
			}

			@Override
			public void postProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				handoff(execution);
			}
			
		};
	}
	
	protected void addEventExecution(JEventExecution eventExecution){
		eventExecution.setPhase(Phase.EVENT_RESULT_GET_READY);
		execute(eventExecution);
	}
	

	
}
