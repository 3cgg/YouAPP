package me.bunny.modular._p.streaming.coordinator;

import java.util.ArrayList;
import java.util.List;

public enum WorkflowErrorCode {

	E0001("E0001",false),
	
	E0002("workflow node is offline",true);
	
	private final String name;
	
	private final boolean recover;
	
	private CauseHolder cause=new CauseHolder();
	
	private static final class CauseHolder {
		final java.util.List<Throwable> cause=new ArrayList<>();
		private void addThrowable(Throwable t){
			cause.add(t);
		}
	}
	
	public WorkflowErrorCode setThrowable(Throwable t){
		cause.addThrowable(t);
		return this;
	}
	
	private WorkflowErrorCode(String name,boolean recover) {
		this.name=name;
		this.recover=recover;
	}
	
	public String getName() {
		return name;
	}

	public boolean isRecover() {
		return recover;
	}
	
	public List<Throwable> getCause() {
		return cause.cause;
	}
	
	void clearCause(){
		cause.cause.clear();
	}
	
}
