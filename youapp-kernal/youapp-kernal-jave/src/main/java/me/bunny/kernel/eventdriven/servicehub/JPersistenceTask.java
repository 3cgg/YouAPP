package me.bunny.kernel.eventdriven.servicehub;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import me.bunny.kernel.jave.model.JModel;


/**
 * the persistence task be populated in {@link JEventExecution#setPersitenceTask(JPersistenceTask)}, the can be called 
 * later while getting from the persistence repository. the is the part of supporting persistence mechanism.
 * {@link #getRunnable()} can provide some runnable.
 * <strong>the sub-class must be accessed from outer.</strong> i.e. it means the class must be <strong>public</strong> modify.
 * @author J
 */
@SuppressWarnings("serial")
public abstract class JPersistenceTask implements JModel{

	public static final String VOID="void";
	
	protected final JEventExecution eventExecution;
	
	public JPersistenceTask(JEventExecution eventExecution){
		this.eventExecution=eventExecution;
	}
	
	/**
	 * execution logical business
	 * @return
	 */
	public abstract Object execute() ;
	
	/**
	 * determinate if the method {@link #execute()} has return value or not.
	 * if the method return true , the method above {@link #execute()} just mentioned is the same as 
	 * <code>void execute()</code> , and the method {@link #execute()} must return {@link Void}
	 * @return
	 */
	public abstract boolean isVoid();
	
	
	/**
	 * for service executor.
	 * @return
	 */
	public final Runnable getRunnable(){
		Runnable runnable=null;
		if(isVoid()){
			runnable=new Runnable() {
				@Override
				public void run() {
					execute();
				}
			};
		}
		else{
			runnable=new FutureTask<Object>(new Callable<Object>() {
						@Override
						public Object call() throws Exception {
							return execute();
						}
					});
		}
		return runnable;
	}
	
}
