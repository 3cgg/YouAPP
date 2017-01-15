package me.bunny.kernel.mock;

import me.bunny.kernel.jave.support._package.JDefaultMethodMeta;
import me.bunny.kernel.jave.support.parser.JParser;

public interface JJSONMockModelParser extends JParser {

	public abstract JMockModel parse(JDefaultMethodMeta methodMeta,
			JMockContext context);

}