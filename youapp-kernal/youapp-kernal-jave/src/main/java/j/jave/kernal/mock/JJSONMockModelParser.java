package j.jave.kernal.mock;

import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.jave.support.parser.JParser;

public interface JJSONMockModelParser extends JParser {

	public abstract JMockModel parse(JDefaultMethodMeta methodMeta,
			JMockContext context);

}