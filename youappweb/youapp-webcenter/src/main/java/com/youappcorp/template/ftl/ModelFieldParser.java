package com.youappcorp.template.ftl;

import java.util.List;

import me.bunny.kernel.jave.support.parser.JParser;

public interface ModelFieldParser extends JParser {

	List<ModelField> parse(Class<?> clazz) throws Exception;
	
}
