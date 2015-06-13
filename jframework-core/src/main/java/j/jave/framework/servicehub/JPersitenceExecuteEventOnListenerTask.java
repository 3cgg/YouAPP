package j.jave.framework.servicehub;


@SuppressWarnings("serial")
public class JPersitenceExecuteEventOnListenerTask extends JPersitenceTask{
	
	public JPersitenceExecuteEventOnListenerTask(JEventExecution eventExecution){
		super(eventExecution);
	}
	
	public Object execute() {
		return JServiceHub.get().executeEventOnListener(eventExecution.getEvent());
	}
	
	@Override
	public boolean isVoid() {
		return false;
	}
	
}
