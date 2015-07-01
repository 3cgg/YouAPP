package j.jave.framework.commons.eventdriven.servicehub;

import java.io.Serializable;

/**
 * IF YOU want to correctly serialize/deserialize the callback instance , 
 * ensure the instance is not dependency on any execution context.
 * The class implements the interface must be explicit name,and also matches the above rule.
 * @author J
 *
 */
public interface JAsyncCallback extends Serializable{

	void callback(Object[] result,JEventExecution eventExecution);
	
}
