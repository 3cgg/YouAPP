package j.jave.kernal.streaming.coordinator;

import java.util.ArrayList;

/**
 * @author JIAZJ
 *
 */
public enum NodeStatus {
	
	ONLINE("ONLINE"),
	
	READY("READY"),
	
	PROCESSING("PROCESSING"),
	
	COMPLETE("COMPLETE"),
	
	COMPLETE_ERROR("COMPLETE_ERROR");
	
	private final String name;
	
	private CauseHolder cause=new CauseHolder();
	
	private static final class CauseHolder {
		final java.util.List<Throwable> cause=new ArrayList<>();
		private void addThrowable(Throwable t){
			cause.add(t);
		}
	}
	
	private NodeStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public CauseHolder getCause() {
		return cause;
	}
	
	public boolean hasError(){
		return !cause.cause.isEmpty();
	}
	
	public void setThrowable(Throwable t){
		if(COMPLETE_ERROR!=this){
			throw new IllegalStateException("ONLY "+COMPLETE_ERROR.name+" can accept exception info.");
		}
		cause.addThrowable(t);
	}
	
	public boolean isComplete(){
		return NodeStatus.COMPLETE.equals(this)
				||NodeStatus.COMPLETE_ERROR.equals(this);
	}

	public boolean isCompleteWithError(){
		return NodeStatus.COMPLETE_ERROR.equals(this);
	}
}
