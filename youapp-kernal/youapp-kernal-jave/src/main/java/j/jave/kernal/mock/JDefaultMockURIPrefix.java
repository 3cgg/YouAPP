package j.jave.kernal.mock;

import j.jave.kernal.container.JScheme;
import j.jave.kernal.container._resource.JResourceURIParserService;
import j.jave.kernal.jave.io.JURIPart;
import j.jave.kernal.jave.support._package.JDefaultMethodMeta;

public class JDefaultMockURIPrefix implements JURIPart<JDefaultMethodMeta> {

	/**
	 * like "file://d:/java_/..."
	 */
	private String root;
	
	public JDefaultMockURIPrefix(String root) {
		if(JScheme.CLASSPATH.getValue().equalsIgnoreCase(root)){
			this.root=JResourceURIParserService.CLASS_PATH_PREFIX;
			return;
		}
		this.root = root;
	}

	@Override
	public String part(JDefaultMethodMeta methodMeta) {
		Class<?> clazz= methodMeta.getClazz();
		return root+"/mock/"+clazz.getPackage().getName().replace(".", "/")+"/";
	}
	
	
}
