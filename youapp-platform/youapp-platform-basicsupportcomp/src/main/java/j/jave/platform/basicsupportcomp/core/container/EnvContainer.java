package j.jave.platform.basicsupportcomp.core.container;

import java.net.URI;

public class EnvContainer implements JExecutor {

	private JRunner runner;
	
	private EnvContainerConfig containerConfig;

	public JRunner getRunner() {
		return runner;
	}

	public void setRunner(JRunner runner) {
		this.runner = runner;
	}

	public EnvContainerConfig getContainerConfig() {
		return containerConfig;
	}

	public void setContainerConfig(EnvContainerConfig containerConfig) {
		this.containerConfig = containerConfig;
	}

	@Override
	public String unique() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean accept(URI uri) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object execute(URI uri, Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
