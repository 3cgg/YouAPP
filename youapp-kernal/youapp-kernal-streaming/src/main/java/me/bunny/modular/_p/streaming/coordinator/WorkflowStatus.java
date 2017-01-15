package me.bunny.modular._p.streaming.coordinator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JIAZJ
 *
 */
public enum WorkflowStatus {
	
	OFFLINE("OFFLINE"),
	
	ONLINE("ONLINE"),
	
	STOP("STOP"),
	
	ERROR("ERROR");
	
	private final String name;
	
	private CauseHolder cause=new CauseHolder();
	
	private static final class CauseHolder {
		final java.util.List<WorkflowErrorCode> cause=new ArrayList<>();
		private void addErrorCode(WorkflowErrorCode errorCode){
			if(cause.contains(errorCode)){
				cause.remove(errorCode);
			}
			cause.add(errorCode);
		}
	}
	
	private WorkflowStatus(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	synchronized WorkflowStatus setErrorCode(WorkflowErrorCode errorCode){
		if(ERROR!=this){
			throw new IllegalStateException("ONLY "+ERROR.name+" can accept exception info.");
		}
		cause.addErrorCode(errorCode);
		return this;
	}
	
	synchronized boolean removeError(WorkflowErrorCode errorCode){
		if(ERROR!=this){
			throw new IllegalStateException("ONLY "+ERROR.name+" can accept exception info.");
		}
		return cause.cause.remove(errorCode);
	}
	
	synchronized boolean containsError(WorkflowErrorCode errorCode){
		if(ERROR!=this){
			throw new IllegalStateException("ONLY "+ERROR.name+" can accept exception info.");
		}
		return cause.cause.contains(errorCode);
	}
	
	synchronized boolean recoverIf(){
		return cause.cause.size()==0;
	}
	
	public List<Throwable> getCause() {
		List<Throwable> causes=new ArrayList<>();
		for(WorkflowErrorCode errorCode:cause.cause){
			causes.addAll(errorCode.getCause());
		}
		return causes;
	}
	
	public boolean hasError(){
		return !cause.cause.isEmpty();
	}
	
	
	
	public boolean isOffline(){
		return WorkflowStatus.OFFLINE==(this)
				;
	}
	
	public boolean isOnline(){
		return WorkflowStatus.ONLINE==(this)
				;
	}

	public boolean isError(){
		return WorkflowStatus.ERROR==(this);
	}
	
	public boolean isStop(){
		return WorkflowStatus.STOP==this;
	}
	
}
