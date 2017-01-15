package me.bunny.modular._p.taskdriven.tkdd.flow;

import me.bunny.modular._p.taskdriven.tkdd.JNotifyStart;

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
