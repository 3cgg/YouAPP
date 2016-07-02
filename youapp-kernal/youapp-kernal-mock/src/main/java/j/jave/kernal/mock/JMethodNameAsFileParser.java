package j.jave.kernal.mock;

import j.jave.kernal.jave.io.JURIPart;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;

public class JMethodNameAsFileParser extends JURIGetJSONMockModelParser {
	
	private JURIPart<? super JDefaultMethodMeta> uriPrefix;
	
	public JMethodNameAsFileParser(JURIPart<? super JDefaultMethodMeta> uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	@Override
	protected String doGetURI(JDefaultMethodMeta methodMeta, JMockContext context) {
		String prefix=uriPrefix.part(methodMeta);
		String fileName=methodMeta.getClazz().getSimpleName()+"."+methodMeta.getMethodName()+".json";
		return prefix+fileName;
	}

}
