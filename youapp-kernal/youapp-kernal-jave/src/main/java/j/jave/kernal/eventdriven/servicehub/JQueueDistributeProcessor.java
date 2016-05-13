package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.JLinkedBlockingQueue;

import java.util.AbstractQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * the class describers a structure of a single queue on which a main thread scans , and poll a head element to a handler {@link EventExecutionHandler} 
 * to process.  generally <strong>only one main thread</strong> is going if the {@link #handler} never provide any task.
 * i.e. {@link #getTaskThreadPoolExecutor()} is called only base on the provided task ,like as not null returned from {@link EventExecutionHandler#taskProvided(Object, AbstractQueue)}
 * @author J
 *
 * @param <T>
 */
public class JQueueDistributeProcessor {
	
	public static class JQueueDistributeProcessorConfig{
		/*
		 * configure
		 */
		/**
		 * the describer
		 */
		private String name;
		
		/**
		 * how many threads the task executing thread {@link #taskThreadPoolExecutor} poll has.
		 */
		private int fixedThreadCount=10;
		
		/**
		 * if setup the execution task.
		 */
		private boolean setup=true;
		
		public boolean isSetup() {
			return setup;
		}

		public void setSetup(boolean setup) {
			this.setup = setup;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getFixedThreadCount() {
			return fixedThreadCount;
		}

		public void setFixedThreadCount(int fixedThreadCount) {
			this.fixedThreadCount = fixedThreadCount;
		}
	}
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private final EventExecutionHandler handler;
	
	private int fixedThreadCount=10;
	
	private String name="";
	/*
	 * configure end
	 */
	
	private boolean setup=true;
	
	private final JEventQueuePipe eventQueuePipe;
	
	private void initConfig(JQueueDistributeProcessorConfig config){
		this.name=config.name;
		this.fixedThreadCount=config.fixedThreadCount;
		this.setup=config.isSetup();
	}
	
	public JQueueDistributeProcessor(EventExecutionHandler handler,
			JQueueDistributeProcessorConfig config,
			JEventQueuePipe eventQueuePipe
			){
		this.handler=handler;
		this.eventQueuePipe=eventQueuePipe;
		initConfig(config);
		setup();
	}
	
	private ThreadPoolExecutor taskThreadPoolExecutor =null;
	
	private ThreadPoolExecutor getTaskThreadPoolExecutor() {
		if(taskThreadPoolExecutor==null){
			synchronized(this){
				if(taskThreadPoolExecutor==null){
					taskThreadPoolExecutor =new ThreadPoolExecutor
							(fixedThreadCount, fixedThreadCount, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());
				}
			}
		}
		return taskThreadPoolExecutor;
	}

	/**
	 * scan all {@link #eventQueue} to pop a highest event to execute.
	 */
	private ExecutorService scanOneByOneExecutor =null;
	
	private synchronized void setup(){
		if(scanOneByOneExecutor==null){
			if(setup){
				scanOneByOneExecutor =Executors.newFixedThreadPool(1,new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						return new Thread(r, getName()+"-ScanOneByOne");
					}
				});
			}
		}
		
		if(periodTaskQueue==null){
			periodTaskQueue=new PeriodTaskQueue();
		}
		
	}
	
	/**
	 * How to process element polled from the queue.
	 * any implementation should consider what need do <strong>should not block the thread</strong>.
	 * if the operation consume much time, consider setup another thread to do so.
	 * @author J
	 *
	 * @param <T>
	 */
	public interface EventExecutionHandler{
		
		/**
		 * offer the element again if true , otherwise processing the element and drop it.
		 * @param execution
		 * @return
		 */
		boolean isLaterProcess(JEventExecution execution);
		
		/**
		 * the method called base on the returned false from {@link #isLaterProcess(Object, AbstractQueue)}.
		 * the provided task will be sent to the other thread, it only provides an another simple chance to affect the event.
		 * @param execution
		 * @return
		 */
		Runnable taskProvided(JEventExecution execution);
	
		/**
		 * the method called base on the  returned false from {@link #isLaterProcess(Object, AbstractQueue)}
		 * @param execution
		 * @param executions
		 */
		void postProcess(JEventExecution execution);
		
		boolean isHandOff(JEventExecution execution);
	}
	
	
	/**
	 * any sub-class may provide JPersistenceTask that can be executed immediately in other thread or later during 
	 * getting from the persistence repository. 
	 * @author J
	 *
	 */
	public abstract static class JAbstractEventExecutionHandler implements EventExecutionHandler {
		
		@Override
		public final Runnable taskProvided(JEventExecution execution) {
			JPersistenceTask persistenceTask=persistenceTask(execution);
			if(persistenceTask==null) {
				// remove current runnable task, release resources.
				execution.setRunnable(null);
				execution.setPersitenceTask(null);
				return null;
			}
			execution.setPersitenceTask(persistenceTask); 
			Runnable runnable=persistenceTask.getRunnable();
			execution.setRunnable(runnable);
			return runnable;
		}
		
		/**
		 * providing a delayed task that need be executed later, or immediately execute the expected task.
		 * @param execution
		 * @return null means there is not later executable task .
		 */
		public abstract JPersistenceTask persistenceTask(JEventExecution execution);
		
		@Override
		public boolean isHandOff(JEventExecution execution) {
			return true;
		}
		
	}


	public String getName() {
		return name;
	}
	
	
	public void addExecution(JEventExecution execute){
		EventExecutionRunnable eventExecutionRunnable=new EventExecutionRunnable(execute, handler);
		if(scanOneByOneExecutor!=null){
			//scanOneByOneExecutor.execute(eventExecutionRunnable);
		}
		periodTaskQueue.addEventExecutionRunnable(eventExecutionRunnable);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	private class EventExecutionRunnable  implements Runnable{
		
		private JEventExecution eventExecution;
		
		private EventExecutionHandler handler;
		
		public EventExecutionRunnable(JEventExecution eventExecution,EventExecutionHandler handler) {
			this.eventExecution = eventExecution;
			this.handler=handler;
		}
		
		@Override
		public void run() {
			if(handler.isLaterProcess(eventExecution)){
				JQueueDistributeProcessor.this.addExecution(eventExecution);
			}
			else{
				Runnable futureTask= handler.taskProvided(eventExecution);
				if(futureTask!=null){
					getTaskThreadPoolExecutor().execute(futureTask);
				}
				handler.postProcess(eventExecution);
				
				if(handler.isHandOff(eventExecution)){
					eventQueuePipe.handoff(eventExecution);
				}
				
			}
		}
	}
	
	private PeriodTaskQueue periodTaskQueue=null;
	
	private class PeriodTaskQueue extends JLinkedBlockingQueue<EventExecutionRunnable>{
		public PeriodTaskQueue() {
			LOGGER.info(leader.getName());
			leader.start();
		}
		private Runnable runnable=new Runnable() {
			@Override
			public void run() {
				final ReentrantLock lock = PeriodTaskQueue.this.lock;
				for(;;){
					try {
						lock.lockInterruptibly();
						EventExecutionRunnable eventExecutionRunnable=poll();
						while (eventExecutionRunnable!=null) {
							
							if(LOGGER.isDebugEnabled()){
								LOGGER.debug(JJSON.get().formatObject(
										eventExecutionRunnable.eventExecution.getEvent().getClass().getName()
										+" <-->"+eventExecutionRunnable.eventExecution.getEvent().getUnique()));
							}
							eventExecutionRunnable.run();
	                    	eventExecutionRunnable=poll();
		                }
						available.await();
                        System.out.println("wakeup...");
					} catch (InterruptedException e) {
						LOGGER.error(e.getMessage(), e);
					}
		            finally {
		                lock.unlock();
		            }
				}
			}
		};
		
		private Thread leader=new Thread(runnable,(JQueueDistributeProcessor.this.name+" period thread."));
		
		private final ReentrantLock lock=new ReentrantLock();
		
		private final Condition available=lock.newCondition();
		
		private final ReentrantLock putLock=new ReentrantLock();
		
		
		private boolean addEventExecutionRunnable(EventExecutionRunnable eventExecutionRunnable){
			boolean offered=true;
			try{
				putLock.lockInterruptibly();
				offered=offer(eventExecutionRunnable);
				if(offered){
					if(lock.tryLock()){
						try{
							available.signalAll();
						}catch(Exception e){
							LOGGER.error(e.getMessage(), e);
						}
						finally{
							lock.unlock();
						}
					}
				}
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}finally{
				putLock.unlock();
			}
			return offered;
		}
		
		
		
	}
	
	
}
