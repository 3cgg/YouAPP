package j.jave.kernal.mock;

import j.jave.kernal.jave.support._package.JDefaultMethodMeta;

public interface JMockClassGetter {

	Class<?> getMockClass(JDefaultMethodMeta methodMeta, JMockContext context) throws Exception;
	
}
