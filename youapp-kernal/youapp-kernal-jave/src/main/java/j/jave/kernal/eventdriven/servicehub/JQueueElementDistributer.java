package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.JEventExecutionQueueElementDistributer.EventExecutionHandler;
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
public class JQueueElementDistributer<T extends JQueueElement> {
	
	public static class JQueueElementDistributerConfig{
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
	
	protected final JQueueElementHandler<T> handler;
	
	private int fixedThreadCount=10;
	
	private String name="";
	/*
	 * configure end
	 */
	
	private boolean setup=true;
	
	private void initConfig(JQueueElementDistributerConfig config){
		this.name=config.name;
		this.fixedThreadCount=config.fixedThreadCount;
		this.setup=config.isSetup();
	}
	
	public JQueueElementDistributer(JQueueElementHandler<T> handler,
			JQueueElementDistributerConfig config
			){
		this.handler=handler;
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
	public interface JQueueElementHandler<T extends JQueueElement>{
		
		/**
		 * offer the element again if true , otherwise processing the element and drop it.
		 * @param execution
		 * @return
		 */
		boolean isLaterProcess(T execution);
		
		/**
		 * the method called base on the returned false from {@link #isLaterProcess(Object, AbstractQueue)}.
		 * the provided task will be sent to the other thread, it only provides an another simple chance to affect the event.
		 * @param execution
		 * @return
		 */
		Runnable taskProvided(T execution);
	
		/**
		 * the method called base on the  returned false from {@link #isLaterProcess(Object, AbstractQueue)}
		 * @param execution
		 * @param executions
		 */
		void postProcess(T execution);
		
		boolean isHandOff(T execution);
	}

	public String getName() {
		return name;
	}
	
	/**
	 * override the method to provide your custom implementation of {@link QueueElementExecutionRunnable}.
	 * @param execution
	 * @return
	 */
	protected QueueElementExecutionRunnable getQueueElementExecutionRunnable(T execution){
		QueueElementExecutionRunnable eventExecutionRunnable=new QueueElementExecutionRunnable(execution, handler);
		return eventExecutionRunnable;
	}
	
	
	public void addExecution(T execution){
		QueueElementExecutionRunnable eventExecutionRunnable=getQueueElementExecutionRunnable(execution);
		if(scanOneByOneExecutor!=null){
			//scanOneByOneExecutor.execute(eventExecutionRunnable);
		}
		periodTaskQueue.addEventExecutionRunnable(eventExecutionRunnable);
	}
	
	public void setName(String name) {
		this.name = name;
	}

	protected class QueueElementExecutionRunnable  implements Runnable{
		
		private T eventExecution;
		
		private JQueueElementHandler<T> handler;
		
		public QueueElementExecutionRunnable(T eventExecution,JQueueElementHandler<T> handler) {
			this.eventExecution = eventExecution;
			this.handler=handler;
		}
		
		@Override
		public void run() {
			if(handler.isLaterProcess(eventExecution)){
				JQueueElementDistributer.this.addExecution(eventExecution);
			}
			else{
				Runnable futureTask= handler.taskProvided(eventExecution);
				if(futureTask!=null){
					getTaskThreadPoolExecutor().execute(futureTask);
				}
				handler.postProcess(eventExecution);
				
				if(handler.isHandOff(eventExecution)){
					handoff(eventExecution);
				}
				
			}
		}
	}
	
	/**
	 * hand off the current element.
	 * @param element
	 */
	protected void handoff(T element){
		
	}
	
	private PeriodTaskQueue periodTaskQueue=null;
	
	private class PeriodTaskQueue extends JLinkedBlockingQueue<QueueElementExecutionRunnable>{
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
						try{
							QueueElementExecutionRunnable eventExecutionRunnable=poll();
							while (eventExecutionRunnable!=null) {
								eventExecutionRunnable.run();
		                    	eventExecutionRunnable=poll();
			                }
						}catch(Exception e){
							LOGGER.error(e.getMessage(), e);
						}
						available.await();
						if(LOGGER.isDebugEnabled()){
							LOGGER.debug(Thread.currentThread().getName()+" wakeup...");
						}
					} catch (InterruptedException e) {
						LOGGER.error(e.getMessage(), e);
					}
		            finally {
		                lock.unlock();
		            }
				}
			}
		};
		
		private Thread leader=new Thread(runnable,(JQueueElementDistributer.this.name+" period thread."));
		
		private final ReentrantLock lock=new ReentrantLock();
		
		private final Condition available=lock.newCondition();
		
		private final ReentrantLock putLock=new ReentrantLock();
		
		
		private boolean addEventExecutionRunnable(QueueElementExecutionRunnable eventExecutionRunnable){
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
	
	public JQueueElementHandler<T> getHandler() {
		return handler;
	}
	
}
