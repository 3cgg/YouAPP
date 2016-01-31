package j.jave.kernal.taskdriven.tkdd;

public interface JTaskExecutePostProcessor {
	
	void postProcessBeforeExecute()  throws JTaskExecutionException;
	
	void postProcessAfterExecute()  throws JTaskExecutionException;
	
}
