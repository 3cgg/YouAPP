package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.model.JModel;

public class EventExecutionResult implements JModel{
	
	private Exception exception;
	
	private Object[] objects;

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public Object[] getObjects() {
		return objects;
	}

	public void setObjects(Object[] objects) {
		this.objects = objects;
	}
	
}
