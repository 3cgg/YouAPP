package j.jave.kernal.taskdriven.tkdd;

public interface JTaskInvocation<T extends JTask> {

	/**
	 * progress of executing intercepters
	 * @return
	 */
	public T  proceed();
	
}
