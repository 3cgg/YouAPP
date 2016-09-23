package com.youappcorp.template.ftl;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

public interface UIListCriterialFieldParser extends UITemplateFieldParser {

	List<UIListCriteriaField> parse(ModelConfig modelConfig) throws Exception;
	
}
