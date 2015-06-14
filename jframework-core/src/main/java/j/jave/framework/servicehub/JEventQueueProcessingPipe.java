package j.jave.framework.servicehub;

import j.jave.framework.servicehub.JEventExecution.Phase;
import j.jave.framework.support.JQueueDistributeProcessor;
import j.jave.framework.support.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;

import java.util.AbstractQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JEventQueueProcessingPipe extends JEventQueuePipe{
	
	JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
	{
		config.setName("JEventQueueProcessingPipe");
	}
	private final JQueueDistributeProcessor<JEventExecution> queueDistributeProcessor
	=new JQueueDistributeProcessor<JEventExecution>(new LinkedBlockingQueue<JEventExecution>(),config);
	{
		queueDistributeProcessor.setHandler(new JAbstractEventExecutionHandler() {
			@Override
			public boolean isLaterProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				try {
					FutureTask<?> futureTask=(FutureTask<?>) execution.getFutureTask();
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
			
		});
	}
	
	protected void addEventExecution(JEventExecution eventExecution){
		eventExecution.setPhase(Phase.EVENT_RESULT_GET_READY);
		queueDistributeProcessor.addExecution(eventExecution);
	}
	

	
}
