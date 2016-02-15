package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JUniqueUtils;

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
	
	private final JEventQueuePipe next(){
		return eventQueuePipeChain.next(this);
	}
	
	/**
	 * the method is the entrance of this pipe , generally retrieve <code>JEventExecution</code>  from the ahead pipe
	 * @param eventExecution
	 */
	protected abstract void addEventExecution(JEventExecution eventExecution);
	
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
