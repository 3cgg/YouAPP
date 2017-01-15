/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;

import java.util.concurrent.TimeoutException;

import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.service.JService;


/**
 * event processor center. all event must be processes by this processor. 
 * And the class also provide the mechanism that manager the processing of how to process the event and the related callback.
 * A event is sent to the event queue incoming named as {@link JEventQueueEventExecutingPipe} ,
 * then processed here to sent to the next one named as {@link JEventQueueEventResultGettingPipe},
 * then to sent to the last one named as {@link JEventQueueEventResultCallBackPipe}, 
 * the last one scan the linked queue and find those completed event then check if the callback needed, if true setup a new TASK to send
 * to the executor called {@code ThreadPoolExecutor}.  
 * In the future the event processing can add persistence mechanism.
 * @author J
 * @see JEventQueueEventExecutingPipe
 * @see JEventQueueEventResultGettingPipe
 * @see JEventQueueEventResultCallBackPipe
 * @see JEventQueuePipeline
 */
class JServiceEventProcessor {

	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private JServiceHub serviceHub=JServiceHub.get();
	
	private JAsyncEventResultStorageService eventResultRepo=JAsyncEventResultStorageServiceUtil.get();
	
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

	private JEventQueuePipeline eventQueuePipeline=new JEventQueuePipeline("EVENT-CORE");

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
			return eventResultRepo.getEventResult(eventUnique);
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
	public void addDelayEvent(JYouAPPEvent<?> event){
		addDelayEvent(event, JYouAPPEvent.NOTHING, false);
	}
	
	/**
	 * put the event in the event queue, can release the thread, i.e. asynchronous
	 * the method is the same as {@link #addDelayEvent(JYouAPPEvent, JAsyncCallback, override)} with the override parameter default true
	 * @param event
	 * @param asyncCallback 
	 */
	public void addDelayEvent(JYouAPPEvent<?> event,JAsyncCallback asyncCallback){
		addDelayEvent(event, asyncCallback, true);
	}
	
	/**
	 * put the event in the event queue, can release the thread, i.e. asynchronous
	 * @param event
	 * @param asyncCallback 
	 * @param override if override the predefined callback if any.
	 */
	public void addDelayEvent(JYouAPPEvent<?> event,JAsyncCallback asyncCallback,boolean override){
		event.addAsyncCallback(asyncCallback, override);
		eventQueuePipeline.addAPPEvent(event);
	}
	
	
	/**
	 * execute the event immediately, need block the thread. i.e. synchronized.
	 * @param event
	 * @return EventExecutionResult , all listener returned. at least an empty object array if no listener found.
	 */
	public EventExecutionResult addImmediateEvent(JYouAPPEvent<?> event){
		return serviceHub.executeEventOnListener(event);
	}
	
	/**
	 * get service, if the service gets lost , thrown some exception.
	 * @param event
	 * @return
	 */
	public JService getService(JServiceGetEvent event){
		return serviceHub.trigger(event);
	}
	
	public void registerService(JServiceRegisterEvent event){
		serviceHub.trigger(event);
	}
}
