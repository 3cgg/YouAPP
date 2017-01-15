package com.youappcorp.template.ftl;

import me.bunny.kernel.taskdriven.tkdd.flow.JFlowContext;

public interface InternalConfigStrategy {
	
	InternalConfig config(JFlowContext flowContext);
	
}
