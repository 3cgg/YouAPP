
package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class PreparedConfigTask extends TemplateTask {

	@Override
	protected Object doRun() throws Exception {
		Class<? extends InternalConfigStrategy> clazz= getConfig().getInternalConfigStrategyClass();
		InternalConfigStrategy configStrategy= clazz.newInstance();
		InternalConfig internalConfig=configStrategy.config(getFlowContext());
		setInternalConfig(internalConfig);
		return null;
	}

}
