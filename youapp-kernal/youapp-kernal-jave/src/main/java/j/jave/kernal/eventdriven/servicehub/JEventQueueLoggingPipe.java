package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JQueueDistributeProcessor.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.json.JJSON;

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
			public boolean isLaterProcess(JEventExecution execution) {
				return false;
			}

			@Override
			public JPersistenceTask persistenceTask(JEventExecution execution) {
				LOGGER.info(" the event processed : "+JJSON.get().formatObject(execution));
				return null;
			}

			@Override
			public void postProcess(JEventExecution execution) {
			}
		};
	}

}
