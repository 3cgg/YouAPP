package me.bunny.kernel.taskdriven.tkdd;

public interface JTaskInvocation {

	/**
	 * progress of executing intercepters
	 * @return
	 */
	public Object  proceed();
	

	public JTask getTask();
}
