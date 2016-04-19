package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.JConfiguration;

public class EnvContainers {
	
	private static EnvContainers envContainers=new EnvContainers(JConfiguration.get());
	
	private EnvContainers(JConfiguration configuration) {
		// TODO Auto-generated constructor stub
	}
	public static EnvContainers get(JConfiguration configuration){
		EnvContainers envContainers=new EnvContainers(configuration);
		return envContainers;
	}
	
	public static EnvContainers get(){
		return envContainers;
	}
	
	public EnvContainer build(EnvContainerConfig containerConfig){
		EnvContainer envContainer=new EnvContainer();
		JRunner runner=containerConfig.getLoader().load();
		envContainer.setRunner(runner);
		envContainer.setContainerConfig(containerConfig);
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
