package j.jave.platform.basicsupportcomp.core.container;

import java.net.URI;

import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.ComponentVersionApplication;

public abstract class JControllerRunner implements JRunner {

	private String unique;
	
	private String name;
	
	private final SpringDynamicJARApplicationCotext dynamicJARApplicationCotext;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	private MicroContainerConfig containerConfig;
	
	public JControllerRunner(SpringDynamicJARApplicationCotext dynamicJARApplicationCotext) {
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
	public final boolean accept(URI uri) {
		return Scheme.CONTROLLER.getValue().equals(uri.getScheme());
	}

	@Override
	public Object execute(URI uri,Object object) {
		throw new JOperationNotSupportedException("sub-class provide the function.");
	}
	
	@Override
	public void setContainerConfig(MicroContainerConfig containerConfig) {
		this.containerConfig=containerConfig;
	}
	
}
