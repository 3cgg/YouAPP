package j.jave.framework.servicehub;

import j.jave.framework.json.JJSON;
import j.jave.framework.servicehub.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;

import java.util.AbstractQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class JEventQueueResultLogger extends JEventQueuePipe {

	JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
	{
		config.setName("JEvent Queue Result Logger");
	}
	private final JQueueDistributeProcessor<JEventExecution> queueDistributeProcessor
	=new JQueueDistributeProcessor<JEventExecution>(new LinkedBlockingQueue<JEventExecution>(),config);
	{
		queueDistributeProcessor.setHandler(new JQueueDistributeProcessor.Handler<JEventExecution>() {

			@Override
			public boolean isLaterProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				return false;
			}

			@Override
			public Runnable taskProvided(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				LOGGER.debug(" the event processed : "+JJSON.get().format(execution));
				return null;
			}

			@Override
			public void postProcess(JEventExecution execution,
					AbstractQueue<JEventExecution> eventExecutions) {
				next().addEventExecution(execution);
			}
		});
	}
	
	@Override
	protected void addEventExecution(JEventExecution eventExecution) {
		queueDistributeProcessor.addExecution(eventExecution);
	}

}
