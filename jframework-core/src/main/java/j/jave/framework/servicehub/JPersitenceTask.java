package j.jave.framework.servicehub;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

@SuppressWarnings("serial")
public abstract class JPersitenceTask implements Serializable{

	public static final String VOID="void";
	
	protected final JEventExecution eventExecution;
	
	public JPersitenceTask(JEventExecution eventExecution){
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
