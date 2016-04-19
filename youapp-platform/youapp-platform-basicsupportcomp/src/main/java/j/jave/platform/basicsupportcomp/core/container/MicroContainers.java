package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.JConfiguration;

public class MicroContainers {
	
	private static MicroContainers envContainers=new MicroContainers(JConfiguration.get());
	
	private MicroContainers(JConfiguration configuration) {
		// TODO Auto-generated constructor stub
	}
	public static MicroContainers get(JConfiguration configuration){
		MicroContainers envContainers=new MicroContainers(configuration);
		return envContainers;
	}
	
	public static MicroContainers get(){
		return envContainers;
	}
	
	public MicroContainer build(MicroContainerConfig containerConfig){
		MicroContainer microContainer=new MicroContainer();
		microContainer.setContainerConfig(containerConfig);
		JRunner runner=containerConfig.getLoader().load(containerConfig);
		microContainer.setRunner(runner);
		return microContainer;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
