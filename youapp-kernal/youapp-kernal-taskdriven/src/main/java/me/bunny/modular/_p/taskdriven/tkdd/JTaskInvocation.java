package me.bunny.modular._p.taskdriven.tkdd;

public interface JTaskInvocation {

	/**
	 * progress of executing intercepters
	 * @return
	 */
	public Object  proceed();
	

	public JTask getTask();
}
