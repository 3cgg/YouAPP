package j.jave.kernal.mock;

import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.jave.support.parser.JParser;

public interface JMockClassMethodParamsParser extends JParser {

	Object[] parseMethodParams( JDefaultMethodMeta defaultMethodMetal, JMockContext context);
	
}
