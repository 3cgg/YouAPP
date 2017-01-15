package me.bunny.kernel.mock;

import me.bunny.kernel.jave.support._package.JDefaultMethodMeta;

public interface JMockClassGetter {

	Class<?> getMockClass(JDefaultMethodMeta methodMeta, JMockContext context) throws Exception;
	
}
