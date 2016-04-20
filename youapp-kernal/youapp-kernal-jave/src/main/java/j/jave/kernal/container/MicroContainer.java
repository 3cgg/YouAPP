package j.jave.kernal.container;


public interface MicroContainer extends JExecutor ,JIdentifier,JLifecycle{

	public JRunner getRunner() ;

	public void setRunner(JRunner runner) ;

	public MicroContainerConfig getContainerConfig() ;

	public void setContainerConfig(MicroContainerConfig containerConfig) ;

	
}
