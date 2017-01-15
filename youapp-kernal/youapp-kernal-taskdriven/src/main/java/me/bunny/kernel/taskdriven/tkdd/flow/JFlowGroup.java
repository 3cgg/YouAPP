package me.bunny.kernel.taskdriven.tkdd.flow;


public interface JFlowGroup extends JFlow , JNotifyStartPut{
	
	public void put(JFlow flow);
	
}
