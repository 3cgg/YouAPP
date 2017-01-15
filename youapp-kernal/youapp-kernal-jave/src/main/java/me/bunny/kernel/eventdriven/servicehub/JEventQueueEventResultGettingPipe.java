package me.bunny.kernel.eventdriven.servicehub;

import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import me.bunny.kernel.eventdriven.servicehub.JEventExecution.Phase;
import me.bunny.kernel.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import me.bunny.kernel.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;

public class JEventQueueEventResultGettingPipe extends JEventQueuePipe{
	
	
	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueEventResultGettingPipe.class.getSimpleName());
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
				}catch(TimeoutException e){
					if(LOGGER.isDebugEnabled()){
//						LOGGER.error(e.getMessage(), e);
					}
					//ignore
					// put to last, give another chance to get result.
					return true;
				} 
				catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					// put to last, give another chance to get result.
					return true;
				}finally{
					execution.setPhase(Phase.EVENT_RESULT_GET_READY);
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
