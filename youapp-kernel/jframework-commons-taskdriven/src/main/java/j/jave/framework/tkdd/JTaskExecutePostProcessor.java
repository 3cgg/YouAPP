package j.jave.framework.tkdd;

public interface JTaskExecutePostProcessor {
	
	void postProcessBeforeExecute()  throws JTaskExecutionException;
	
	void postProcessAfterExecute()  throws JTaskExecutionException;
	
}
