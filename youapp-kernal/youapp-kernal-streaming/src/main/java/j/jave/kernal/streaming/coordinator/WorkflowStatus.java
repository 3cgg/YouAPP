package j.jave.kernal.streaming.coordinator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JIAZJ
 *
 */
public enum WorkflowStatus {
	
	OFFLINE("OFFLINE"),
	
	ONLINE("ONLINE"),
	
	ERROR("ERROR");
	
	private final String name;
	
	private CauseHolder cause=new CauseHolder();
	
	private static final class CauseHolder {
		final java.util.List<Throwable> cause=new ArrayList<>();
		private void addThrowable(Throwable t){
			cause.add(t);
		}
	}
	
	private WorkflowStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Throwable> getCause() {
		return cause.cause;
	}
	
	public boolean hasError(){
		return !cause.cause.isEmpty();
	}
	
	public WorkflowStatus setThrowable(Throwable t){
		if(ERROR!=this){
			throw new IllegalStateException("ONLY "+ERROR.name+" can accept exception info.");
		}
		cause.addThrowable(t);
		return this;
	}
	
	public boolean isOffline(){
		return WorkflowStatus.OFFLINE.equals(this)
				;
	}
	
	public boolean isOnline(){
		return WorkflowStatus.ONLINE.equals(this)
				;
	}

	public boolean isError(){
		return WorkflowStatus.ERROR.equals(this);
	}
	
}
