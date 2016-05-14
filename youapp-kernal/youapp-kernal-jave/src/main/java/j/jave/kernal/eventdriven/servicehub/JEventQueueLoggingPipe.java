package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.json.JJSON;

public class JEventQueueLoggingPipe extends JEventQueuePipe {

	
	@Override
	protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
		JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
		config.setName(JEventQueueLoggingPipe.class.getSimpleName());
		return config;
	}
	
	private static int count=1;
	
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
