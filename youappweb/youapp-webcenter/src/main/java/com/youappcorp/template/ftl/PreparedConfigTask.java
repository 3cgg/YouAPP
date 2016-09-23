
package com.youappcorp.template.ftl;

import j.jave.kernal.jave.utils.JAssert;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class PreparedConfigTask extends TemplateTask {

	@Override
	protected Object doRun() throws Exception {
		
		Config config=getConfig();
		
		JAssert.isNotNull(config.getUiRelativePath(), " ui relative path is missing ... ");
		JAssert.isNotEmpty(config.getUiRelativePath(), " ui relative path is missing ... ");
		
		Class<? extends InternalConfigStrategy> clazz= getConfig().getInternalConfigStrategyClass();
		InternalConfigStrategy configStrategy= clazz.newInstance();
		InternalConfig internalConfig=configStrategy.config(getFlowContext());
		setInternalConfig(internalConfig);
		return null;
	}

}
