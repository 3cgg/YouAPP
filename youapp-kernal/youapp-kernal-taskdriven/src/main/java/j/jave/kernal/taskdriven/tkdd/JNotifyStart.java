package j.jave.kernal.taskdriven.tkdd;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;

public interface JNotifyStart {

	/**
	 * notify task framework to ready to process this task.
	 * @return
	 */
	Object start() throws Exception ;
	
	/**
	 * notify task framework to ready to process this task with the provided context
	 * @param flowContext
	 * @return
	 */
	Object start(JFlowContext flowContext)  throws Exception;
	
}
