/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.exception.JEventException;
import j.jave.kernal.eventdriven.servicehub.eventlistener.JServiceExistsEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestEndNotifyEvent;
import j.jave.kernal.eventdriven.servicehub.notify.JEventRequestStartNotifyEvent;
import j.jave.kernal.jave.service.JService;



/**
 * service hub delegate. 
 * @author J
 */
public class JServiceHubDelegate {

	private static JServiceHubDelegate serviceHubDelegate=new JServiceHubDelegate();;
	private JServiceHubDelegate(){}
	
	public static JServiceHubDelegate get(){
//		if(serviceHubDelegate==null){
//			synchronized (JServiceHubDelegate.class) {
//				serviceHubDelegate=new JServiceHubDelegate();
//			}
//		}
		return serviceHubDelegate;
	}
	
	private final JServiceEventProcessor serviceEventProcessor=JServiceEventProcessor.get();
	
	/**
	 * register service 
	 * @param object
	 * @param serviceName
	 * @param serviceFactory
	 */
	public void register(Object object,Class<?> serviceName,JServiceFactory<?> serviceFactory){
		JServiceRegisterEvent registerEvent=new JServiceRegisterEvent(object, serviceName, serviceFactory);
		serviceEventProcessor.registerService(registerEvent);
	}
	
	/**
	 * get service lazily, the service proxy returned,
	 * @param object the caller.
	 * @param clazz
	 * @return
	 */
	public  <T> T getService(Object object,Class<T> clazz){
		return JServiceLazyProxy.proxy(object, clazz);
	}
	
	/**
	 * get service, if the service gets lost , thrown some exception.
	 * @param object
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T> T getActualService(Object object,Class<T> clazz){
		JServiceGetEvent getEvent=new JServiceGetEvent(object, clazz);
		return (T) serviceEventProcessor.getService(getEvent);
	}
	
	/**
	 * delegate the event to the event processor,
	 * the event later is to be executed by processor,  unexpected immediately,
	 * if the event contains asynchronous event callback , the callback will be triggered in the later as a different thread.
	 * @param event
	 */
	public void addDelayEvent(JAPPEvent<?> event){
		serviceEventProcessor.addDelayEvent(new JEventRequestStartNotifyEvent(this,event));
		serviceEventProcessor.addDelayEvent(event);
	}
	
	
	/**
	 * delegate the event to the event processor,
	 * the event later is to be executed by processor,  unexpected immediately, a callback mechanism can be used to retrieve notify
	 * after the event is proceed. 
	 * <Strong>A callback is asynchronous represented as its name.</Strong>
	 * @param event
	 * @param asyncCallback  the parameter override the predefined callback in the event instance if any.
	 */
	public void addDelayEvent(JAPPEvent<?> event,JAsyncCallback asyncCallback){
		serviceEventProcessor.addDelayEvent(new JEventRequestStartNotifyEvent(this,event));
		serviceEventProcessor.addDelayEvent(event,asyncCallback);
	}
	
	/**
	 * delegate the event to the event processor,
	 * the event later is to be executed by processor,  unexpected immediately, a callback mechanism can be used to retrieve notify
	 * after the event is proceed. 
	 * <Strong>A callback is asynchronous represented as its name.</Strong>
	 * @param event
	 * @param asyncCallback  the parameter override the predefined callback in the event instance if any.
	 * @param override override the predefined callback if true , otherwise append callback in callback chain.
	 */
	public void addDelayEvent(JAPPEvent<?> event,JAsyncCallback asyncCallback,boolean override){
		serviceEventProcessor.addDelayEvent(new JEventRequestStartNotifyEvent(this,event));
		serviceEventProcessor.addDelayEvent(event,asyncCallback,override);
	}
	
	/**
	 * delegate the event to the event processor,
	 * the event is to be executed immediately by processor.
	 * <p>any exception which occurs in the runtime can be thrown for the caller.
	 * @param event
	 */
	public Object[] addImmediateEvent(JAPPEvent<?> event){
		addDelayEvent(new JEventRequestStartNotifyEvent(this,event));
		EventExecutionResult eventExecutionResult=serviceEventProcessor.addImmediateEvent(event);
		addDelayEvent(new JEventRequestEndNotifyEvent(this,event));
		if(eventExecutionResult.getException()!=null){
			throw new JEventException(eventExecutionResult.getException());
		}
		return eventExecutionResult.getObjects();
	}
	
	/**
	 * delegate the event to the event processor,
	 * the event is to be executed immediately by processor. if many listeners listen the event , the first listener executed 
	 * result returned (note the result is random, because the listeners don't have any order.), 
	 * also note the object may cannot cast the target class you defined.
	 * <p>any exception which occurs in the runtime can be thrown for the caller.
	 * @param event
	 * @return <T> the expected type. if no listener found, <code>null</code> returned.
	 * @see {@link ClassCastException}
	 */
	@SuppressWarnings("unchecked")
	public <T> T addImmediateEvent(JAPPEvent<?> event,Class<T> clazz) throws JEventException{
		addDelayEvent(new JEventRequestStartNotifyEvent(this,event));
		EventExecutionResult eventExecutionResult=serviceEventProcessor.addImmediateEvent(event);
		addDelayEvent(new JEventRequestEndNotifyEvent(this,event));
		if(eventExecutionResult.getException()!=null){
			throw new JEventException(eventExecutionResult.getException());
		}
		Object[] objects=eventExecutionResult.getObjects();
		if(objects.length>0){
			return (T) objects[0];
		}
		return null;
	}
	
	
	public boolean existsService(Class<? extends JService> clazz){
		Object[] objects=addImmediateEvent(new JServiceExistsEvent(this, clazz));
		if(objects.length<1){
			return false;
		}
		else{
			return (boolean) objects[0];
		}
	}
	
}
