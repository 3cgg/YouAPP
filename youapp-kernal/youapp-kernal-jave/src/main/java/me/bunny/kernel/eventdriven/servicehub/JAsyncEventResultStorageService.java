package me.bunny.kernel.eventdriven.servicehub;

import me.bunny.kernel.jave.service.JService;

/**
 * store the event result service ,which is called by {@link JEventQueueEventResultPersistencePipe} .
 * Any system can implements the service to provide other storage other than {@link JDefaultAsyncEventResultStorageService}.
 * <p>note: the implementation should register itself in the service hub,otherwise it is not efficient.
 * @author J
 * @see JEventQueueEventResultPersistencePipe
 * @see JDefaultAsyncEventResultStorageService
 */
public interface JAsyncEventResultStorageService extends JService {

	EventExecutionResult getEventResult(String eventUnique) throws JEventExecutionException;
	
	void putEventResult(JEventExecution eventExecution);
}