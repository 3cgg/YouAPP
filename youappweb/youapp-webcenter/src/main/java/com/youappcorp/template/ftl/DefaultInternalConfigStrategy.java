package com.youappcorp.template.ftl;

import me.bunny.modular._p.taskdriven.tkdd.flow.JFlowContext;

public class DefaultInternalConfigStrategy implements InternalConfigStrategy {
	
	@Override
	public InternalConfig config(JFlowContext flowContext) {
		Config config=TemplateUtil.getConfig(flowContext);
		InternalConfig internalConfig=new DefaultInternalConfig(config);
		return internalConfig;
	}
	
}
