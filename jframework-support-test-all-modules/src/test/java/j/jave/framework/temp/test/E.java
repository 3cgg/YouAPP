/**
 * 
 */
package j.jave.framework.temp.test;

import j.jave.framework.commons.utils.JDateUtils;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @author Administrator
 *
 */
public class E {

	static Runnable doSelf=new Runnable() {
		
		@Override
		public void run() {
			
			try{
				
				while(true){
					Thread.sleep(2000);
					System.out.println(JDateUtils.formatWithSeconds(new Date()));
				}
				
			}catch(InterruptedException e){
				System.out.println(Thread.currentThread().getName()+" end:"+JDateUtils.formatWithSeconds(new Date()));
				daemon.execute(doSelf);
			}
			catch(Exception e){
				System.out.println("doSelf:"+e.getMessage()+" thread status:"+doSelfThread.getState());
				e.printStackTrace();
				
				while(true){
					if(doSelfThread.isInterrupted()){
						System.out.println("iiiiiiiiiiii");
					}
					System.out.println(JDateUtils.formatWithSeconds(new Date()));
				}
				
				
			}
			
		}
	};
	
	static private ExecutorService daemon=Executors.newFixedThreadPool(1,new ThreadFactory() {
		
		@Override
		public Thread newThread(Runnable r) {
			doSelfThread=new Thread(r,"doSelf");
			return doSelfThread;
		}
	});
	static {
		//daemon.execute(findListener);
	}
	static Thread doSelfThread;
	
	public static void main(String[] args) {
		
		
		daemon.execute(doSelf);
		
		while(true){
			try {
				Thread.sleep(5000);
				
				
			} catch (InterruptedException e) {
				System.out.println("main:"+e.getMessage());
				e.printStackTrace();
			}
			doSelfThread.interrupt();
			
		}
		
		
		
	}
	
	
	
	
}
