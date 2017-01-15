package me.bunny.kernel.mock;

import me.bunny.kernel.jave.support._package.JDefaultMethodMeta;
import me.bunny.kernel.jave.support.parser.JParser;

public interface JMockClassMethodParamsParser extends JParser {

	Object[] parseMethodParams( JDefaultMethodMeta defaultMethodMetal, JMockContext context);
	
}
