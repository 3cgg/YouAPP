package me.bunny.modular._p.taskdriven.tkdd.flow;

public interface JTaskFlowCommonConfig {

	boolean isRoot();
	
	/**
	 * set the flow as the root of the whole job.
	 * @param root
	 */
	void setRoot(boolean root);
	
	String getName();
	
	/**
	 * set description.
	 * @param name
	 */
	void setName(String name);
	
}
