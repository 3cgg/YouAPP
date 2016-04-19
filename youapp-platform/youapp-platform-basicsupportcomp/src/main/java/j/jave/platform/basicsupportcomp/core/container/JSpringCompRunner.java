package j.jave.platform.basicsupportcomp.core.container;

import java.net.URI;

import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.ComponentVersionApplication;

public class JSpringCompRunner implements JRunner {

	private String unique;
	
	private String name;
	
	private final SpringDynamicJARApplicationCotext dynamicJARApplicationCotext;
	
	private final ComponentVersionApplication componentVersionApplication;
	
	public JSpringCompRunner(SpringDynamicJARApplicationCotext dynamicJARApplicationCotext) {
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
	public boolean accept(URI uri) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object execute(URI uri,Object object) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
