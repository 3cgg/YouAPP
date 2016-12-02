package j.jave.kernal.streaming.coordinator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JIAZJ
 *
 */
public enum NodeStatus {
	
	ONLINE("ONLINE"),
	
	READY("READY"),
	
	PROCESSING("PROCESSING"),
	
	COMPLETE("COMPLETE"),
	
	COMPLETE_ERROR("COMPLETE_ERROR"),
	
	COMPLETE_SKIP("COMPLETE_SKIP");
	
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
	
	public List<Throwable> getCause() {
		return cause.cause;
	}
	
	public boolean hasError(){
		return !cause.cause.isEmpty();
	}
	
	public NodeStatus setThrowable(Throwable t){
		if(COMPLETE_ERROR!=this){
			throw new IllegalStateException("ONLY "+COMPLETE_ERROR.name+" can accept exception info.");
		}
		cause.addThrowable(t);
		return this;
	}
	
	public boolean isComplete(){
		return NodeStatus.COMPLETE.equals(this)
				||NodeStatus.COMPLETE_ERROR.equals(this)
				||NodeStatus.COMPLETE_SKIP.equals(this)
				;
	}

	public boolean isCompleteWithError(){
		return NodeStatus.COMPLETE_ERROR.equals(this);
	}
	
	public boolean isSkip(){
		return NodeStatus.COMPLETE_SKIP.equals(this);
	}
}
