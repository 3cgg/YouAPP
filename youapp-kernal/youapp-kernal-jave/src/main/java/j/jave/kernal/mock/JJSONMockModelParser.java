package j.jave.kernal.mock;

import j.jave.kernal.jave.support._package.JDefaultMethodMeta;

public interface JJSONMockModelParser {

	public abstract JMockModel parse(JDefaultMethodMeta methodMeta,
			JMockContext context);

}