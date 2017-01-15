package me.bunny.kernel.container;


public interface JMicroContainer extends JExecutor ,JIdentifier,JLifecycle{

	public JRunner getRunner() ;

	public void setRunner(JRunner runner) ;

	public JMicroContainerConfig getContainerConfig() ;

	public void setContainerConfig(JMicroContainerConfig containerConfig) ;

	
}
