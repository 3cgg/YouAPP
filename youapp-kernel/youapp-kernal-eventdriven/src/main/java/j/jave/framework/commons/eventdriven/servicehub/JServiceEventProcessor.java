/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.service.JService;

import java.util.concurrent.TimeoutException;


/**
 * event processor center. all event must be processes by this processor. 
 * And the class also provide the mechanism that manager the processing of how to process the event and the related callback.
 * A event is sent to the event queue incoming named as {@link JEventQueueINPipe} ,
 * then processed here to sent to the next one named as {@link JEventQueueProcessingPipe},
 * then to sent to the last one named as {@link JEventQueueOUTPipe}, 
 * the last one scan the linked queue and find those completed event then check if the callback needed, if true setup a new TASK to send
 * to the executor called {@code ThreadPoolExecutor}.  
 * In the future the event processing can add persistence mechanism.
 * @author J
 * @see JEventQueueINPipe
 * @see JEventQueueProcessingPipe
 * @see JEventQueueOUTPipe
 * @see JEventQueuePipes
 */
class JServiceEventProcessor {

	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private JServiceHub serviceHub=JServiceHub.get();
	
	private static JServiceEventProcessor eventProcessor=null;
	
	private JServiceEventProcessor(){}
	
	public static JServiceEventProcessor get(){
		if(eventProcessor==null){
			synchronized (JServiceEventProcessor.class) {
				eventProcessor=new JServiceEventProcessor();
			}
		}
		return eventProcessor;
	}

	private JEventQueuePipes eventQueuePipeChain=new JEventQueuePipes();
	
	/*
	 * others begin
	 */
	
	/**
	 * get event result.
	 * @param eventUnique  unique property value in event object.
	 * @param wait how many time of second unit . 
	 * @return null returned if the unique not bing to a event. 
	 * @throws JEventExecutionException
	 * @throws TimeoutException
	 */
	public Object getEventResult(String eventUnique,long wait) throws JEventExecutionException{
		try{
			return eventQueuePipeChain.getEventResult(eventUnique);
		}catch(Exception e){
			if(wait>0){
				try {
					Thread.sleep(wait*1000);
				} catch (InterruptedException e1) {
					LOGGER.info(e1.getMessage(), e1);
				}
				return getEventResult(eventUnique, 0);
			}
			else{
				throw new JEventExecutionException(JEventExecutionException.EVENT_NOT_COMPLETE,
						"event is still not executed completely, or please check you pass correct unique id ! please wait.");
			}
		}
	}
	
	/**
	 * put the event in the event queue, can release the thread, i.e. asynchronous
	 * @param event
	 */
	public void addDelayEvent(JAPPEvent<?> event){
		addDelayEvent(event, null, false);
	}
	
	/**
	 * put the event in the event queue, can release the thread, i.e. asynchronous
	 * the method is the same as {@link #addDelayEvent(JAPPEvent, JAsyncCallback, override)} with the override parameter default true
	 * @param event
	 * @param asyncCallback 
	 */
	public void addDelayEvent(JAPPEvent<?> event,JAsyncCallback asyncCallback){
		addDelayEvent(event, asyncCallback, true);
	}
	
	/**
	 * put the event in the event queue, can release the thread, i.e. asynchronous
	 * @param event
	 * @param asyncCallback 
	 * @param override if override the predefined callback if any.
	 */
	public void addDelayEvent(JAPPEvent<?> event,JAsyncCallback asyncCallback,boolean override){
		
		//clear the callback chain.
		JAPPEventOperator.clearAttachedAsyncCallbackChain(event);
		
		if(override){
			//put the custom callback.
			if(asyncCallback!=null){
				JAPPEventOperator.addAttachedAsyncCallback(event, asyncCallback);
			}
		}
		else{
			// put predefined callback into the callback chain.
			if(event.getAsyncCallback()!=null){
				JAPPEventOperator.addAttachedAsyncCallback(event, event.getAsyncCallback());
			}
			
			//put the custom callback.
			if(asyncCallback!=null){
				JAPPEventOperator.addAttachedAsyncCallback(event, asyncCallback);
			}
		}
		eventQueuePipeChain.addAPPEvent(event);
	}
	
	
	/**
	 * execute the event immediately, need block the thread. i.e. synchronized.
	 * @param event
	 * @return Object[] , all listener returned. at least an empty object array if no listener found.
	 */
	public Object[] addImmediateEvent(JAPPEvent<?> event){
		return serviceHub.executeEventOnListener(event);
	}
	
	public JService getService(JServiceGetEvent event){
		return serviceHub.trigger(event);
	}
	
	public void registerService(JServiceRegisterEvent event){
		serviceHub.trigger(event);
	}
}
