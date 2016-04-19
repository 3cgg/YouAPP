package j.jave.platform.basicsupportcomp.core.container;

import java.net.URI;

public class MicroContainer implements JExecutor ,JIdentifier,JContainer{

	private JRunner runner;
	
	private MicroContainerConfig containerConfig;

	public JRunner getRunner() {
		return runner;
	}

	public void setRunner(JRunner runner) {
		this.runner = runner;
	}

	public MicroContainerConfig getContainerConfig() {
		return containerConfig;
	}

	public void setContainerConfig(MicroContainerConfig containerConfig) {
		this.containerConfig = containerConfig;
	}

	@Override
	public String unique() {
		return containerConfig.getUnique();
	}

	@Override
	public String name() {
		return null;
	}

	@Override
	public boolean accept(URI uri) {
		return runner.accept(uri);
	}

	@Override
	public Object execute(URI uri, Object object) {
		return runner.execute(uri, object);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
