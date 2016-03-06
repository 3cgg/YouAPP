package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.service.JService;

public interface JAsyncEventResultRepoService extends JService {

	Object getEventResult(String eventUnique) throws JEventExecutionException;
	
	void putEventResult(JEventExecution eventExecution);
}