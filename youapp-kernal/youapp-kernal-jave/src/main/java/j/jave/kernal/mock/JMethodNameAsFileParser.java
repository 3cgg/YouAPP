package j.jave.kernal.mock;

import j.jave.kernal.container._resource.JResourceURIParserService;
import j.jave.kernal.jave.io.JURIPart;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;

import java.net.URI;

public class JMethodNameAsFileParser extends JURIGetJSONMockModelParser {
	
	private JURIPart<? super JDefaultMethodMeta> uriPrefix;
	
	public JMethodNameAsFileParser(JURIPart<? super JDefaultMethodMeta> uriPrefix) {
		this.uriPrefix = uriPrefix;
	}

	@Override
	protected String doGetURI(JDefaultMethodMeta methodMeta, JMockContext context) {
		String prefix=uriPrefix.part(methodMeta);
		String fileName=methodMeta.getClazz().getSimpleName()+"."+methodMeta.getMethodName()+".json";
		String tempURIStr=prefix+fileName;
		try{
			if(prefix.startsWith(JResourceURIParserService.CLASS_PATH_PREFIX)){
				return resourceContainer.resourceURIParser().parse4ClassPath(tempURIStr).toString();
			}
			return resourceContainer.resourceURIParser().parse(new URI(tempURIStr)).toString();
		}catch(Exception e){
			throw new JMockException(e);
		}
	}

}
