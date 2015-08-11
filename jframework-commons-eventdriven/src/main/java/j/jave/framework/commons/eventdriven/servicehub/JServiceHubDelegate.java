/**
 * 
 */
package j.jave.framework.commons.eventdriven.servicehub;


/**
 * service hub delegate. 
 * @author J
 */
public class JServiceHubDelegate {

	private static JServiceHubDelegate serviceHubDelegate=null;
	private JServiceHubDelegate(){}
	
	public static JServiceHubDelegate get(){
		
		if(serviceHubDelegate==null){
			synchronized (JServiceHubDelegate.class) {
				serviceHubDelegate=new JServiceHubDelegate();
			}
		}
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
	 * get service 
	 * @param object the caller.
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T> T getService(Object object,Class<T> clazz){
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
		serviceEventProcessor.addDelayEvent(event,asyncCallback,override);
	}
	
	/**
	 * delegate the event to the event processor,
	 * the event is to be executed immediately by processor.
	 * @param event
	 */
	public Object[] addImmediateEvent(JAPPEvent<?> event){
		return serviceEventProcessor.addImmediateEvent(event);
	}
	
	/**
	 * delegate the event to the event processor,
	 * the event is to be executed immediately by processor. if many listeners listen the event , the first listener executed 
	 * result returned (note the result is random, because the listeners don't have any order.), 
	 * also note the object may cannot cast the class you defined.
	 * @param event
	 * @return <T> the expected type. if no listener found, <code>null</code> returned.
	 * @see {@link ClassCastException}
	 */
	@SuppressWarnings("unchecked")
	public <T> T addImmediateEvent(JAPPEvent<?> event,Class<T> clazz){
		Object[] objects=serviceEventProcessor.addImmediateEvent(event);
		if(objects.length>0){
			return (T) objects[0];
		}
		return null;
	}
	
}