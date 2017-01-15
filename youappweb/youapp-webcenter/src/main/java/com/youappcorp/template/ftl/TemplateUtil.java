package com.youappcorp.template.ftl;

import java.beans.Introspector;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import me.bunny.modular._p.taskdriven.tkdd.flow.JFlowContext;

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
	
	public static void setModelConfig(JFlowContext flowContext,ModelConfig modelConfig){
		flowContext.put(KeyNames.TEMPLATE_MODEL_CONFIG_KEY, modelConfig);
	}
	
	public static String variableName(String className){
		return Introspector.decapitalize(className);
	}
	
	public static String type(Field field){
		String type=KeyNames.FIELD_TYPE_STRING;
		Class<?> fieldType=field.getType();
		if(fieldType==String.class){
			type=KeyNames.FIELD_TYPE_STRING;
		}else if(fieldType==byte.class
				||fieldType==short.class
				||fieldType==int.class	
				||fieldType==long.class
				||fieldType==float.class
				||fieldType==double.class
				||fieldType==Byte.class
				||fieldType==Short.class
				||fieldType==Integer.class
				||fieldType==Long.class
				||fieldType==Float.class
				||fieldType==Double.class
				||fieldType==BigDecimal.class
				){
			type=KeyNames.FIELD_TYPE_NUMERIC;
		}else if(Date.class.isAssignableFrom(fieldType)){
			type=KeyNames.FIELD_TYPE_DATE;
		}
		return type;
	}
	
	
}
