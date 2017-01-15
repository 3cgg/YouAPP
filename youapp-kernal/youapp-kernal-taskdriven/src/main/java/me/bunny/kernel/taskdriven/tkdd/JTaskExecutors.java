package me.bunny.kernel.taskdriven.tkdd;


public class JTaskExecutors {
	
	private JTaskExecutors(){}
	
	public static JTaskExecutor newSingleTaskExecutor(){
		
		return JTaskExecutor.taskExecutor;
	}
	
	static class JTaskExecutor{
		
		static JTaskExecutor taskExecutor=new JTaskExecutor();
		
		public Object execute(JTask task){
			JTaskInvocation taskInvocation= task.getTaskInvocation();
			return taskInvocation.proceed();
		}
		
	}
	
}
