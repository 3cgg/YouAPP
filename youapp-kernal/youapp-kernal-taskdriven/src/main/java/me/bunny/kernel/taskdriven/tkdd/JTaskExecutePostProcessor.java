package me.bunny.kernel.taskdriven.tkdd;

public interface JTaskExecutePostProcessor {
	
	void postProcessBeforeExecute()  throws JTaskExecutionException;
	
	void postProcessAfterExecute()  throws JTaskExecutionException;
	
}
