package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.support.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;

import java.util.AbstractQueue;

public class JEventQueueLoggingPipe extends JEventQueuePipe {

	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueLoggingPipe.class.getName());
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
				LOGGER.info(" the event processed : "+JJSON.get().formatObject(execution));
				return null;
			}

			@Override
			public void postProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				handoff(execution);
			}
		};
	}
	
	@Override
	public void addEventExecution(JEventExecution eventExecution) {
		execute(eventExecution);
	}

}
