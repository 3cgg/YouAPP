package j.jave.kernal.mock;

import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.JResourceContainer;
import j.jave.kernal.container.JResourceContainerConfig;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;
import j.jave.kernal.jave.utils.JStringUtils;

public abstract class JURIGetJSONMockModelParser implements JJSONMockModelParser {

	private JDefaultJSONMockModelParser defaultJSONMockModelParser
	=new JDefaultJSONMockModelParser(); 
	
	JResourceContainer resourceContainer= JContainerDelegate.get().getContainer(JResourceContainerConfig.DEFAULT_UNIQUE);
	
	@Override
	public JMockModel parse(JDefaultMethodMeta methodMeta, JMockContext context) {
		JMockModel mockModel=defaultJSONMockModelParser.parse(methodMeta, context);
		mockModel.setData("");
		if(JStringUtils.isNullOrEmpty(mockModel.getUri())){
			mockModel.setUri(doGetURI(methodMeta, context));
		}
		return mockModel;
	}
	
	protected abstract String doGetURI(JDefaultMethodMeta methodMeta, JMockContext context);
	
}
