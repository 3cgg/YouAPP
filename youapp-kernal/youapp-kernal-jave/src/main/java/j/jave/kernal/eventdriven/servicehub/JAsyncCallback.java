package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.model.JModel;

/**
 * IF YOU want to correctly serialize/deserialize the callback instance , 
 * ensure the instance is not dependency on any execution context.
 * The class implements the interface must be explicit name,and also matches the above rule.
 * @author J
 *
 */
public interface JAsyncCallback extends JModel{

	void callback(EventExecutionResult result,JEventExecution eventExecution);
	
}
