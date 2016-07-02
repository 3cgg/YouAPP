package j.jave.kernal.mock;

import j.jave.kernal.jave.support._package.JDefaultMethodMeta;

public abstract class JURIGetJSONMockModelParser implements JJSONMockModelParser {

	private JDefaultJSONMockModelParser defaultJSONMockModelParser
	=new JDefaultJSONMockModelParser(); 
	
	@Override
	public JMockModel parse(JDefaultMethodMeta methodMeta, JMockContext context) {
		JMockModel mockModel=defaultJSONMockModelParser.parse(methodMeta, context);
		mockModel.setData("");
		mockModel.setUri(doGetURI(methodMeta, context));
		return mockModel;
	}
	
	protected abstract String doGetURI(JDefaultMethodMeta methodMeta, JMockContext context);
	
}
