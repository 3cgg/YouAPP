package j.jave.framework.tkdd;


public class JTaskExecutors {
	
	private JTaskExecutors(){}
	
	public static JTaskExecutor newSingleTaskExecutor(){
		
		return JTaskExecutor.taskExecutor;
	}
	
	static class JTaskExecutor{
		
		static JTaskExecutor taskExecutor=new JTaskExecutor();
		
		public Object execute(JTask task){
			JTaskInvocation<JTask> taskInvocation= task.getTaskInvocation();
			taskInvocation.proceed();
			return task.run();
		}
		
	}
	
}
