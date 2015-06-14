package j.jave.framework.servicehub;

import j.jave.framework.support.JQueueDistributeProcessor.Handler;

import java.util.AbstractQueue;

/**
 * any sub-class may provide JPersistenceTask that can be executed immediately in other thread or later during 
 * getting from the persistence repository. 
 * @author J
 *
 */
public abstract class JAbstractEventExecutionHandler implements Handler<JEventExecution> {
	
	@Override
	public final Runnable taskProvided(JEventExecution execution,
			AbstractQueue<JEventExecution> executions) {
		JPersistenceTask persistenceTask=persistenceTask(execution, executions);
		if(persistenceTask==null) return null;
		execution.setPersitenceTask(persistenceTask); 
		Runnable runnable=persistenceTask.getRunnable();
		execution.setFutureTask(runnable);
		return runnable;
	}
	
	/**
	 * provide a task that can be executed later. returning null means there is not task execute.
	 * @param execution
	 * @param executions
	 * @return
	 */
	public abstract JPersistenceTask persistenceTask(JEventExecution execution,
			AbstractQueue<JEventExecution> executions);
	
}
