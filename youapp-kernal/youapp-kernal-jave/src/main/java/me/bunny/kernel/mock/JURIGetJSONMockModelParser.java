package me.bunny.kernel.mock;

import me.bunny.kernel.container.JContainerDelegate;
import me.bunny.kernel.container.JResourceContainer;
import me.bunny.kernel.container.JResourceContainerConfig;
import me.bunny.kernel.jave.support._package.JDefaultMethodMeta;
import me.bunny.kernel.jave.utils.JStringUtils;

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
