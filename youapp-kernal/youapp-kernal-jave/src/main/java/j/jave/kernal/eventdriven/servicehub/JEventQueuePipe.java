package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JUniqueUtils;

/**
 * Any subclass extends from this , which provides the function of monitoring the event queue processing. 
 * @author J
 */
public abstract class JEventQueuePipe {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	/**
	 * be put in the pipeline.
	 */
	private JEventQueuePipeline eventQueuePipeline;
	
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
	
	private JEventExecutionQueueElementDistributer queueDistributeProcessor;
	
	public JEventQueuePipe() {
	}
	
	void initialize(){
		JQueueDistributeProcessorConfig config=getQueueDistributeProcessorConfig();
		config.setName(config.getName()+"-"+getName());
		queueDistributeProcessor
		=new JEventExecutionQueueElementDistributer(getHandler(),config,this);
	}

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
		return eventQueuePipeline.next(this);
	}
	
	/**
	 * the method is the entrance of this pipe , generally retrieve <code>JEventExecution</code>  from the ahead pipe.
	 * anyone can do additional things such as setting event phase,logging any information,etc.
	 * then can pass the event to processor via calling {@link #execute(JEventExecution)} 
	 * @param eventExecution
	 */
	void addEventExecution(JEventExecution eventExecution){
		if(canProcessing(eventExecution)){
			prepareProcessing(eventExecution);
			execute(eventExecution);
		}
		else{
			if(isHandOff(eventExecution)){
				//hand off to next pipe.
				handoff(eventExecution);
			}
		}
	}
	
	/**
	 * ready to join into processor.  
	 * @param eventExecution
	 * @return true if can 
	 */
	protected boolean canProcessing(JEventExecution eventExecution){
		return true;
	}
	
	protected boolean isHandOff(JEventExecution eventExecution){
		return true;
	}
	/**
	 * prepare to process.  
	 * @param eventExecution
	 * @return true if can 
	 */
	protected void prepareProcessing(JEventExecution eventExecution){
	}
	
	/**
	 * hand out the event to processor.
	 * @param eventExecution
	 */
	private void execute(JEventExecution eventExecution){
		queueDistributeProcessor.addExecution(eventExecution);
	}
	
	/**
	 * hand off the <code>JEventExecution</code> to next pipe.
	 * @param eventExecution
	 */
	final void handoff(JEventExecution eventExecution){
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
	
	void setEventQueuePipeline(JEventQueuePipeline eventQueuePipeline) {
		this.eventQueuePipeline = eventQueuePipeline;
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
