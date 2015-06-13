package j.jave.framework.servicehub;

import j.jave.framework.exception.JOperationNotSupportedException;
import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.logging.JLoggerFactory;
import j.jave.framework.utils.JUniqueUtils;


public abstract class JEventQueuePipe {
	
	private JEventQueuePipeChain eventQueuePipeChain;
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private final String unique=JUniqueUtils.unique();
	
	private int order;
	
	protected final JEventQueuePipe next(){
		return eventQueuePipeChain.next(this);
	}
	
	protected abstract void addEventExecution(JEventExecution eventExecution);
	
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
	
	void addAPPEvent(JAPPEvent<? > appEvent){
		throw new JOperationNotSupportedException("not supported.");
	}
	
}
