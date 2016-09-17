package com.youappcorp.template.ftl;

import j.jave.kernal.jave.support.parser.JParser;

import java.util.List;

public interface ModelFieldParser extends JParser {

	List<ModelField> parse(Class<?> clazz) throws Exception;
	
}
