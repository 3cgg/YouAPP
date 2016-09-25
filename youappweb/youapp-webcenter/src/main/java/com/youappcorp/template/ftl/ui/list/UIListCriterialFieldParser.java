package com.youappcorp.template.ftl.ui.list;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ui.UITemplateFieldParser;

public interface UIListCriterialFieldParser extends UITemplateFieldParser {

	List<UIListCriteriaField> parse(ModelConfig modelConfig) throws Exception;
	
}
