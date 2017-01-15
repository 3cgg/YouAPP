package me.bunny.kernel.mock;

import me.bunny.kernel._c.support._package.JDefaultMethodMeta;
import me.bunny.kernel._c.support.parser.JParser;

public interface JMockClassMethodParamsParser extends JParser {

	Object[] parseMethodParams( JDefaultMethodMeta defaultMethodMetal, JMockContext context);
	
}
