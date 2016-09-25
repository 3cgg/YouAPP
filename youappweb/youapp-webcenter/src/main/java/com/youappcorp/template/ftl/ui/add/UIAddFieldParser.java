package com.youappcorp.template.ftl.ui.add;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ui.UITemplateFieldParser;

public interface UIAddFieldParser extends UITemplateFieldParser {

	List<UIAddField> parse(ModelConfig modelConfig) throws Exception;
	
}
