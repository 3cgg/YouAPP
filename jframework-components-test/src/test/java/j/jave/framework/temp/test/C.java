/**
 * 
 */
package j.jave.framework.temp.test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author Administrator
 *
 */
public class C {
	static int i=0;
	
	private static ScheduledThreadPoolExecutor eventExecutor=new ScheduledThreadPoolExecutor(10);
	
	static FutureTask<Object> futureTask=new FutureTask<Object>(new Callable<Object>() {
		public Object call() throws Exception {
			return i++;
		};
	});
	
	public static void main(String[] args) {
		eventExecutor.execute(futureTask);
	}
	
}
