package me.bunny.modular._p.taskdriven.tkdd;

public interface JTaskExecutePostProcessor {
	
	void postProcessBeforeExecute()  throws JTaskExecutionException;
	
	void postProcessAfterExecute()  throws JTaskExecutionException;
	
}
