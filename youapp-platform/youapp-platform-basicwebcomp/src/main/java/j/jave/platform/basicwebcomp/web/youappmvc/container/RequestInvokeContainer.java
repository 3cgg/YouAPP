package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainer;
import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.JExecutor;
import j.jave.kernal.container.JIdentifier;
import j.jave.platform.basicsupportcomp.core.container.SpringCompMicroContainer;
import j.jave.platform.basicsupportcomp.core.container.SpringCompMicroContainerConfig;
import j.jave.platform.basicsupportcomp.core.container.SpringContainerConfig;

import java.net.URI;

public class RequestInvokeContainer implements JExecutor,JIdentifier,JContainer {

	private String unique;
	
	private String name;
	
	private SpringCompMicroContainer springCompMicroContainer;
	
	private ControllerMicroContainer controllerMicroContainer;
	
	protected final SpringContainerConfig springContainerConfig;
	
	public RequestInvokeContainer(SpringContainerConfig springContainerConfig) {
		this.springContainerConfig=springContainerConfig;
		this.name=springContainerConfig.getName();
		this.unique=springContainerConfig.getUnique();
		init(springContainerConfig);
		JContainerDelegate.get().register(this);
	}
	
	private void init(SpringContainerConfig springContainerConfig){
		SpringCompMicroContainerConfig springCompMicroContainerConfig=
				new SpringCompMicroContainerConfig();
		springCompMicroContainer=new SpringCompMicroContainer
				(springContainerConfig,springCompMicroContainerConfig);
		ControllerMicroContainerConfig controllerMicroContainerConfig=
				new ControllerMicroContainerConfig(springCompMicroContainer.getDynamicJARApplicationCotext());
		controllerMicroContainer=new ControllerMicroContainer(springContainerConfig, controllerMicroContainerConfig);
	}
	
	@Override
	public String unique() {
		return this.unique;
	}

	@Override
	public String name() {
		return this.name;
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

}
