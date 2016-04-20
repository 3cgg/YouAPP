package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.kernal.container.Scheme;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.ComponentVersionApplication;

import java.net.URI;

public class ControllerRunner implements JRunner {

	private String unique;
	
	private String name;
	
	private final SpringDynamicJARApplicationCotext dynamicJARApplicationCotext;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	private ControllerMicroContainerConfig controllerMicroContainerConfig;
	
	public ControllerRunner(SpringDynamicJARApplicationCotext dynamicJARApplicationCotext) {
		this.dynamicJARApplicationCotext=dynamicJARApplicationCotext;
		this.componentVersionApplication=dynamicJARApplicationCotext.getComponentVersionApplication();
	}
	
	@Override
	public String unique() {
		return unique;
	}
	
	@Override
	public String name() {
		return name;
	}

	@Override
	public void setContainerConfig(MicroContainerConfig containerConfig) {
		this.controllerMicroContainerConfig=(ControllerMicroContainerConfig) containerConfig;
	}
	
	private static final String GET=Scheme.CONTROLLER.getValue()+"://get?unique={}&path={}";
	
	private static final String PUT=Scheme.CONTROLLER.getValue()+"://put?unique={}&path={}";
	
	@Override
	public final boolean accept(URI uri) {
		return Scheme.CONTROLLER.getValue().equals(uri.getScheme());
	}

	@Override
	public Object execute(URI uri,Object object) {
		throw new JOperationNotSupportedException("sub-class provide the function.");
	}
	
}
