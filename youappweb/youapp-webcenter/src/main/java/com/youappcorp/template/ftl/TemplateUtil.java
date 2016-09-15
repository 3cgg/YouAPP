package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;

public class TemplateUtil {

	public static Config getConfig(JFlowContext flowContext){
		Config config= (Config) flowContext.get(KeyNames.TEMPLATE_CONFIG_KEY);
		return config;
	}
	
	public static void setConfig(JFlowContext flowContext,Config config){
		flowContext.put(KeyNames.TEMPLATE_CONFIG_KEY, config);
	}
	
	public static InternalConfig getInternalConfig(JFlowContext flowContext){
		InternalConfig internalConfig= (InternalConfig) flowContext.get(KeyNames.TEMPLATE_INTERNAL_CONFIG_KEY);
		return internalConfig;
	}
	
	public static void setInternalConfig(JFlowContext flowContext,InternalConfig internalConfig){
		flowContext.put(KeyNames.TEMPLATE_INTERNAL_CONFIG_KEY, internalConfig);
	}
	
	
	
	
}
