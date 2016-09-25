package com.youappcorp.template.ftl.ui.view;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ui.UITemplateFieldParser;

public interface UIViewFieldParser extends UITemplateFieldParser {

	List<UIViewField> parse(ModelConfig modelConfig) throws Exception;
	
}
