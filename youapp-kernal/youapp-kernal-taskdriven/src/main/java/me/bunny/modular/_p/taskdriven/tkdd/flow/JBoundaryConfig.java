package me.bunny.modular._p.taskdriven.tkdd.flow;

public class JBoundaryConfig {

	private boolean active;
	
	private boolean catchException;
	
	private ExceptionHandler exceptionHandler;
	
	public static interface ExceptionHandler{
		void handler(Exception e);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isCatchException() {
		return catchException;
	}

	public void setCatchException(boolean catchException) {
		this.catchException = catchException;
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
	
	
}
