package com.youappcorp.template.ftl;

import j.jave.kernal.jave.support.parser.JParser;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

public interface UIListCriterialFieldParser extends JParser {

	List<UIListCriteriaField> parse(ModelConfig modelConfig) throws Exception;
	
}
