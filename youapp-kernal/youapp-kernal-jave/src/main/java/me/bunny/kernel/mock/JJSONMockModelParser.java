package me.bunny.kernel.mock;

import me.bunny.kernel._c.support._package.JDefaultMethodMeta;
import me.bunny.kernel._c.support.parser.JParser;

public interface JJSONMockModelParser extends JParser {

	public abstract JMockModel parse(JDefaultMethodMeta methodMeta,
			JMockContext context);

}