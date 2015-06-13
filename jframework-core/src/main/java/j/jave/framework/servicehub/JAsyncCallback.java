package j.jave.framework.servicehub;

import java.io.Serializable;

/**
 * IF YOU want to serialize the callback instance , 
 * ensure that the instance is not related to any execution context.
 * i.e. the class implements the interface must be explicit name,and also matches the above rule.
 * @author J
 *
 */
public interface JAsyncCallback extends Serializable{

	void callback(Object[] result,JEventExecution eventExecution);
	
}
