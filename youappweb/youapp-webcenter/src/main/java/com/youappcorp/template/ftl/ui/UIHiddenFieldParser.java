package com.youappcorp.template.ftl.ui;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

public interface UIHiddenFieldParser extends UITemplateFieldParser {

	List<UIField> parse(ModelConfig modelConfig) throws Exception;
	
}
