package j.jave.framework.servicehub;

import j.jave.framework.json.JJSON;
import j.jave.framework.support.JQueueDistributeProcessor;
import j.jave.framework.support.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JEventQueueResultLoggerPipe extends JEventQueuePipe {

	JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
	{
		config.setName("JEventQueueResultLoggerPipe");
	}
	private final JQueueDistributeProcessor<JEventExecution> queueDistributeProcessor
	=new JQueueDistributeProcessor<JEventExecution>(new LinkedBlockingQueue<JEventExecution>(),config);
	{
		queueDistributeProcessor.setHandler(new JAbstractEventExecutionHandler() {

			@Override
			public boolean isLaterProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				return false;
			}

			@Override
			public JPersistenceTask persistenceTask(JEventExecution execution,
					AbstractQueue<JEventExecution> executions) {
				LOGGER.debug(" the event processed : "+JJSON.get().format(execution));
				return null;
			}

			@Override
			public void postProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				handoff(execution);
			}
		});
	}
	
	@Override
	public void addEventExecution(JEventExecution eventExecution) {
		queueDistributeProcessor.addExecution(eventExecution);
	}

}
