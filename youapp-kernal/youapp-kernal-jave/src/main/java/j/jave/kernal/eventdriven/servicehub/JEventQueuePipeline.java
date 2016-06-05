package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.context.JEventDrivenContext;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JAbstractEventExecutionHandler;
import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.JQueueDistributeProcessorConfig;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.JPriorityBlockingQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * the event queue chain thats link different event queue pipe, the default order is 
 * <pre>
 * {@link JEventQueueEventExecutingPipe} -> {@link JEventQueueEventResultGettingPipe} -> {@link JEventQueueEventResultCallBackPipe} -> ...( custom event queue )... -> {@link JEventQueueEndPipe}
 * </pre>
 * @author J
 *@see JEventQueueEventExecutingPipe
 *@see JEventQueueEventResultGettingPipe
 *@see JEventQueueEventResultPersistencePipe
 *@see JEventQueueEventResultCallBackPipe
 *@see JEventQueueEndPipe
 */
public class JEventQueuePipeline {
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());

	private final List<JEventQueuePipe> eventQueuePipes=new ArrayList<JEventQueuePipe>(6);
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected void initPipeline(){
		int order=-1; 
		register(JEventQueueEventExecutingPipe.class, ++order);
		register(JEventQueueEventResultGettingPipe.class, ++order);
		register(JEventQueueEventResultPersistencePipe.class, ++order);
		register(JEventQueueEventResultCallBackPipe.class, ++order);
		if(LOGGER.isDebugEnabled()){
			register(JEventQueueResultLoggerPipe.class, ++order);
		}
		
		JPriorityBlockingQueue<JEventQueuePipeInfo> eventQueuePipeQueue= JEventDrivenContext.get().getEventQueuePipeProvider().getEventQueuePipes();
		while(!eventQueuePipeQueue.isEmpty()){
			JEventQueuePipeInfo eventQueuePipeInfo=eventQueuePipeQueue.poll();
			register(eventQueuePipeInfo.clazz, ++order);
		}

		register(JEventQueueEndPipe.class, ++order);
	
	}
	
	public JEventQueuePipeline(String name){
		this.name=name;
		initPipeline();
	}
	
	
	protected final JEventQueuePipe register(Class<? extends JEventQueuePipe> clazz,int order){
		try {
			JEventQueuePipe eventQueuePipe= clazz.newInstance();
			eventQueuePipe.setOrder(order);
			eventQueuePipe.setName(name);
			eventQueuePipe.setEventQueuePipeline(this);
			eventQueuePipe.initialize();
			eventQueuePipes.add(eventQueuePipe);
			return eventQueuePipe;
		} catch (Exception e) {
			throw new JInitializationException(e);
		} 
	}
	
	
	protected static class JEventQueueEndPipe extends JEventQueuePipe{
		
		@Override
		protected JAbstractEventExecutionHandler getHandler() {
			return new JAbstractEventExecutionHandler() {
				@Override
				public void postProcess(JEventExecution execution) {
				}
				@Override
				public boolean isLaterProcess(JEventExecution execution) {
					return false;
				}
				@Override
				public JPersistenceTask persistenceTask(JEventExecution execution) {
					LOGGER.debug("[unique : "+execution.getEvent().getUnique()+"] is completely executed.");
					return null;
				}
				@Override
				public boolean isHandOff(JEventExecution execution) {
					return false;
				}
			};
		}
		
		@Override
		protected JQueueDistributeProcessorConfig getQueueDistributeProcessorConfig() {
			JQueueDistributeProcessorConfig config=new JQueueDistributeProcessorConfig();
			config.setSetup(true);
			config.setName(JEventQueueEndPipe.class.getSimpleName());
			return config;
		}
	}
	
	/**
	 * get next pipe from the chain, the next pipe is found out according to the order index plus one
	 * based on the passing pipe. 
	 * @param current if the argument is null,the first(ahead) pipe is returned.
	 * @return
	 */
	final JEventQueuePipe next(JEventQueuePipe current){
		
		if(current==null){
			return eventQueuePipes.get(0);
		}
		
		int index=(1+current.getOrder());
		if(index<eventQueuePipes.size()){
			return eventQueuePipes.get(index);
		}
		return null;
	}
	
	/**
	 * JAPPEvent entrance.
	 * @param appEvent
	 */
	protected void addAPPEvent(JAPPEvent<? > appEvent){
		JEventQueuePipe eventQueuePipe=next(null);
		try{
			eventQueuePipe.addAPPEvent(appEvent);
//			if(eventExecution==null){
//				throw new JEventExecutionException("the overrided method 'addAPPEvent' in the event queue pipe [name:"+eventQueuePipe.getName()+",uinique:"+eventQueuePipe.getUnique()+"] must return JEventExecution instance.");
//			}
		}catch(JOperationNotSupportedException e){
			throw new JEventExecutionException("event queue pipe [name:"+eventQueuePipe.getName()+",uinique:"+eventQueuePipe.getUnique()+"] must override the method { JEventExecution addAPPEvent(JAPPEvent<? > appEvent) }");
		}
	}
	
	public static class JEventQueuePipeInfo implements Comparable<JEventQueuePipeInfo>{
		
		private Class<? extends JEventQueuePipe>  clazz;
		
		private String message;
		
		private int order;
		
		public JEventQueuePipeInfo(Class<? extends JEventQueuePipe>  clazz,int order){
			this.clazz=clazz;
			this.order=order;
		}

		public Class<? extends JEventQueuePipe> getClazz() {
			return clazz;
		}

		public void setClazz(Class<? extends JEventQueuePipe> clazz) {
			this.clazz = clazz;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		@Override
		public int compareTo(JEventQueuePipeInfo o) {
			return this.order-o.order;
		}
	}
	
	public static interface JEventQueuePipeProvider {

		void addEventQueuePipe(JEventQueuePipeInfo eventQueuePipeInfo);

		JPriorityBlockingQueue<JEventQueuePipeInfo> getEventQueuePipes();

	}
	
	
	
}
