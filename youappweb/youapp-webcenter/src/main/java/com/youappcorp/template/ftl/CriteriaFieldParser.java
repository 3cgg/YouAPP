package com.youappcorp.template.ftl;

import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import me.bunny.kernel._c.support.parser.JParser;


public interface CriteriaFieldParser extends JParser {

	List<ModelField> parse(ModelConfig modelConfig) throws Exception;
	
}
