package com.youappcorp.template.ftl.ui.edit;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ui.UITemplateFieldParser;

public interface UIEditFieldParser extends UITemplateFieldParser {

	List<UIEditField> parse(ModelConfig modelConfig) throws Exception;
	
}
