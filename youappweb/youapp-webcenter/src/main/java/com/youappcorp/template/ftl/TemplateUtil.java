package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

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
	
	static void setInternalConfig(JFlowContext flowContext,InternalConfig internalConfig){
		flowContext.put(KeyNames.TEMPLATE_INTERNAL_CONFIG_KEY, internalConfig);
	}
	
	
	public static ModelConfig getModelConfig(JFlowContext flowContext){
		ModelConfig modelConfig= (ModelConfig) flowContext.get(KeyNames.TEMPLATE_MODEL_CONFIG_KEY);
		return modelConfig;
	}
	
	static void setModelConfig(JFlowContext flowContext,ModelConfig modelConfig){
		flowContext.put(KeyNames.TEMPLATE_MODEL_CONFIG_KEY, modelConfig);
	}
	
	
}
