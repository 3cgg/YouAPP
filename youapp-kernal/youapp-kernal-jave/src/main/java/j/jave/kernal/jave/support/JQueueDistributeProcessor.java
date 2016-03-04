package j.jave.kernal.jave.support;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

import java.util.AbstractQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * the class describers a structure of a single queue on which a main thread scans , and poll a head element to a handler {@link Handler} 
 * to process.  generally <strong>only one main thread</strong> is going if the {@link #handler} never provide any task.
 * i.e. {@link #getTaskThreadPoolExecutor()} is called only base on the provided task ,like as not null returned from {@link Handler#taskProvided(Object, AbstractQueue)}
 * @author J
 *
 * @param <T>
 */
public class JQueueDistributeProcessor<T> {
	
	public static class JQueueDistributeProcessorConfig{
		/*
		 * configure
		 */
		/**
		 * the describer
		 */
		private String name;
		
		/**
		 * how many elements per second the main thread poll from {@link #executions}
		 */
		private int countPerSec=100;
		
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

		public int getCountPerSec() {
			return countPerSec;
		}

		public void setCountPerSec(int countPerSec) {
			this.countPerSec = countPerSec;
		}

		public int getFixedThreadCount() {
			return fixedThreadCount;
		}

		public void setFixedThreadCount(int fixedThreadCount) {
			this.fixedThreadCount = fixedThreadCount;
		}
	}
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private final AbstractQueue<T> executions;
	
	private int pollbackCapacity=1000;
	
	private JLinkedBlockingQueue<T> pollbacks=null;
	
	private Handler<T> handler;
	
	/*
	 * configure begin
	 */
	private int countPerSec=100;
	
	private int fixedThreadCount=10;
	
	private String name="";
	/*
	 * configure end
	 */
	
	private boolean setup=true;
	
	private void initConfig(JQueueDistributeProcessorConfig config){
		this.name=config.name;
		this.countPerSec=config.countPerSec;
		this.fixedThreadCount=config.fixedThreadCount;
		this.pollbackCapacity=countPerSec*2;
		pollbacks=new JLinkedBlockingQueue<T>(pollbackCapacity);
		this.setup=config.isSetup();
	}
	
	public JQueueDistributeProcessor(AbstractQueue<T> executions,JQueueDistributeProcessorConfig config){
		this.executions=executions;
		initConfig(config);
		setup();
	}
	
	public JQueueDistributeProcessor(AbstractQueue<T> executions,Handler<T> handler,JQueueDistributeProcessorConfig config){
		this.executions=executions;
		this.handler=handler;
		initConfig(config);
		setup();
	}
	
	private LinkedBlockingQueue<Runnable> linkedBlockingQueue=new LinkedBlockingQueue<Runnable>();
	
	private ThreadPoolExecutor taskThreadPoolExecutor =null;
	
	private ThreadPoolExecutor getTaskThreadPoolExecutor() {
		if(taskThreadPoolExecutor==null){
			synchronized(this){
				if(taskThreadPoolExecutor==null){
					taskThreadPoolExecutor =new ThreadPoolExecutor
							(fixedThreadCount, fixedThreadCount, 1, TimeUnit.SECONDS, linkedBlockingQueue,new ThreadPoolExecutor.CallerRunsPolicy());
				}
			}
		}
		return taskThreadPoolExecutor;
	}
	
	final int mainThreadSleepTime=1000; //millisecond
	
	final int maxFlushPollbackTime=30; // around the defined second
	
	int flushPollbackTime=0;
	
	private boolean isFlushPollback(){
		return ++flushPollbackTime>maxFlushPollbackTime;
	}

	private void flushPollback(){
		executions.addAll(pollbacks);
		pollbacks.clear();
		flushPollbackTime=0;
	}
	
	
	Runnable scanOneByOneTask=new Runnable() {
		@Override
		public void run() {
			try{
				while(true){
					//sleep 1 second.
					Thread.sleep(mainThreadSleepTime);
					
					if(executions.size()>0){
						// do with the head elements in the EventQueue every 1 second.
						for(int i=0;i<countPerSec;i++){
							if(executions.size()>0){
								final T execution= executions.poll();
								if(handler.isLaterProcess(execution,executions)){
									if(pollbacks.size()==pollbackCapacity||isFlushPollback()){
										flushPollback();
									}
									pollbacks.offer(execution);
								}
								else{
									Runnable futureTask= handler.taskProvided(execution, executions);
									if(futureTask!=null){
										getTaskThreadPoolExecutor().execute(futureTask);
									}
									handler.postProcess(execution, executions);
								}
							}
						}
					}
				}
			}catch(InterruptedException e){
				LOGGER.error("Interrupted by any thread: ",e);
				LOGGER.info("Resume the thread. ");
				// resume the task. 
				scanOneByOneExecutor.execute(scanOneByOneTask);
			}
			catch(Exception e){
				LOGGER.error("Event Main Thread occurs an exception by : ",e);
				LOGGER.info("Resume the thread. ");
				// resume the task. 
				scanOneByOneExecutor.execute(scanOneByOneTask);
			}
			catch(Throwable e){
				LOGGER.error("Event Main Thread occurs an exception by : ",e);
				LOGGER.info("Resume the thread. ");
				throw e;
			}
		}
	};
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
				scanOneByOneExecutor.execute(scanOneByOneTask);
			}
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
	public interface Handler<T>{
		
		/**
		 * offer the element again if true , otherwise processing the element and drop it.
		 * @param execution
		 * @param executions
		 * @return
		 */
		boolean isLaterProcess(T execution,AbstractQueue<T> executions);
		
		/**
		 * the method called base on the returned false from {@link #isLaterProcess(Object, AbstractQueue)}.
		 * the provided task will be sent to the other thread, it only provides an another simple chance to affect the event.
		 * @param execution
		 * @param executions
		 * @return
		 */
		Runnable taskProvided(T execution,AbstractQueue<T> executions);
	
		/**
		 * the method called base on the  returned false from {@link #isLaterProcess(Object, AbstractQueue)}
		 * @param execution
		 * @param executions
		 */
		void postProcess(T execution,AbstractQueue<T> executions);
		
	}

	public String getName() {
		return name;
	}
	
	
	public void addExecution(T execute){
		executions.offer(execute);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setHandler(Handler<T> handler) {
		this.handler = handler;
	}
	
}
