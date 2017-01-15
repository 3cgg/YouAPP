package me.bunny.kernel.eventdriven.servicehub;

import me.bunny.kernel.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import me.bunny.kernel.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;
import me.bunny.kernel.jave.json.JJSON;

public class JEventQueueLoggingPipe extends JEventQueuePipe {

	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueLoggingPipe.class.getSimpleName());
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
					LOGGER.debug(" the event processed : "+JJSON.get().formatJSONObject(execution));
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
