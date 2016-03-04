package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.JQueueDistributeProcessor;
import j.jave.kernal.jave.support.JQueueDistributeProcessor.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.utils.JUniqueUtils;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Any subclass extends from this , which provides the function of monitoring the event queue processing. 
 * @author J
 */
public abstract class JEventQueuePipe {
	
	/**
	 * be put in the pipe chain.
	 */
	private JEventQueuePipeChain eventQueuePipeChain;
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	/**
	 * the unique identification realted to this pipe.
	 */
	private final String unique=JUniqueUtils.unique();
	
	/**
	 * this pipe is the order index in the pipe chain.
	 */
	private int order;
	
	/**
	 * the simple friendly name.
	 */
	private String name;
	
	private final JQueueDistributeProcessor<JEventExecution> queueDistributeProcessor
	=new JQueueDistributeProcessor<JEventExecution>(new LinkedBlockingQueue<JEventExecution>(),
			getHandler(),
			getQueueDistributeProcessorConfig());
	
	/**
	 * configure the queue distributing processor
	 * @return
	 */
	protected abstract JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig();
	
	/**
	 * executing the event now.
	 * @return
	 */
	protected abstract JAbstractEventExecutionHandler getHandler();
	
	private final JEventQueuePipe next(){
		return eventQueuePipeChain.next(this);
	}
	
	/**
	 * the method is the entrance of this pipe , generally retrieve <code>JEventExecution</code>  from the ahead pipe.
	 * anyone can do additional things such as setting event phase,logging any information,etc.
	 * then can pass the event to processor via calling {@link #execute(JEventExecution)} 
	 * @param eventExecution
	 */
	protected abstract void addEventExecution(JEventExecution eventExecution);
	
	/**
	 * hand out the event to processor.
	 * @param eventExecution
	 */
	protected void execute(JEventExecution eventExecution){
		queueDistributeProcessor.addExecution(eventExecution);
	}
	
	/**
	 * hand off the <code>JEventExecution</code> to next pipe.
	 * @param eventExecution
	 */
	public final void handoff(JEventExecution eventExecution){
		next().addEventExecution(eventExecution);
	}
	
	public String getUnique() {
		return unique;
	}

	public int getOrder() {
		return order;
	}

	void setOrder(int order) {
		this.order = order;
	}
	
	void setEventQueuePipeChain(JEventQueuePipeChain eventQueuePipeChain) {
		this.eventQueuePipeChain = eventQueuePipeChain;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	void addAPPEvent(JAPPEvent<? > appEvent){
		throw new JOperationNotSupportedException("not supported.");
	}
	
	
}
