package j.jave.kernal.taskdriven.tkdd.flow;

import j.jave.kernal.taskdriven.tkdd.JNotifyStart;

public interface JBoundary extends JNotifyStart {
	
	/**
	 * on or off the boundary.
	 * @param boundaryConfig
	 * @return
	 */
	boolean onOff(JBoundaryConfig boundaryConfig);
	
	boolean catchException(JBoundaryConfig boundaryConfig);
	
	/**
	 * all start methods should be delegate to this.
	 * @param flowContext
	 * @return
	 */
	Object delegate(JFlowContext flowContext);

}
