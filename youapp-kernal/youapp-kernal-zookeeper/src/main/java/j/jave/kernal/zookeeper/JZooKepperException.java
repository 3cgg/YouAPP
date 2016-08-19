package j.jave.kernal.zookeeper;

@SuppressWarnings("serial")
public class JZooKepperException extends RuntimeException {
	
	public JZooKepperException(String message){
		super(message);
	}
	
	
	
	public JZooKepperException(Exception e){
		super(e);
	}
	
}
