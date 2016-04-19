package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.platform.basicsupportcomp.core.container.AbstractBaseContainer;
import j.jave.platform.basicsupportcomp.core.container.ContainerConfig;
import j.jave.platform.basicsupportcomp.core.container.MicroContainer;

public class DynamicJARContainer  extends AbstractBaseContainer{

	private MicroContainer controllerContext;
	
	public DynamicJARContainer(ContainerConfig containerConfig) {
		super(containerConfig);
//		DynamicControllerRunner controllerRunner=new DynamicControllerRunner(springContext.get)
	}

}
