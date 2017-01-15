package test.j.jave.kernal.jave;

import org.junit.Test;

import me.bunny.kernel._c.async.JAsyncExecutor;
import me.bunny.kernel._c.async.JAsyncTaskExecutingService;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import test.j.jave.kernal.eventdriven.TestEventSupport;

public class TestAsyncTask extends TestEventSupport {

	private JAsyncTaskExecutingService asyncTaskExecutingService=JServiceHubDelegate.get().getService(this, JAsyncTaskExecutingService.class);
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void testAsyncTask(){
		for(int i=0;i<10;i++){
			asyncTaskExecutingService.addAsyncTask(i,new JAsyncExecutor<Integer>() {
				@Override
				public Object execute(Integer data) throws Exception {
					System.out.println("DATA : " +data);
					return null;
				}
			});
		}
		
		for(int i=0;i<10;i++){
			asyncTaskExecutingService.addAsyncTask(new JAsyncExecutor() {
				@Override
				public Object execute(Object data) throws Exception {
					System.out.println("DATA ( no data ) : " +data);
					return null;
				}
			});
		}
		
		
		System.out.println("edn ;;;;;;;;;;;;");
	}
	
	
}
