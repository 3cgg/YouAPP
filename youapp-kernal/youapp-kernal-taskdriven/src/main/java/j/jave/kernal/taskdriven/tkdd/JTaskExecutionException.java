package j.jave.kernal.taskdriven.tkdd;

@SuppressWarnings("serial")
public class JTaskExecutionException extends RuntimeException {

	public JTaskExecutionException(String message){
		super(message);
	}
	
	public JTaskExecutionException(Exception e){
		super(e);
	}
}
