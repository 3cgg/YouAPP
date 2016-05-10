package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JQueueDistributeProcessor.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.json.JJSON;

public class JEventQueueResultLoggerPipe extends JEventQueuePipe {

	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueResultLoggerPipe.class.getName());
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
				try{
					LOGGER.debug(" the event processed : "+JJSON.get().formatObject(execution));
				}catch(Exception e){
					LOGGER.error(e.getMessage()+";"+execution.getEvent().getSource(), e);
				}
				return null;
			}

			@Override
			public void postProcess(JEventExecution execution) {
			}
		};
	}
}
