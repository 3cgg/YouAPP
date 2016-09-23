package com.youappcorp.template.ftl;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

public interface UIListTableFieldParser extends UITemplateFieldParser {

	List<UIListTableField> parse(ModelConfig modelConfig) throws Exception;
	
}
