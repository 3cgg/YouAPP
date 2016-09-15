package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;

public class DefaultInternalConfigStrategy implements InternalConfigStrategy {
	
	@Override
	public InternalConfig config(JFlowContext flowContext) {
		Config config=TemplateUtil.getConfig(flowContext);
		String modelPath=config.getModelPath();
		InternalConfig internalConfig=new DefaultInternalConfig(modelPath);
		return internalConfig;
	}
	
}
