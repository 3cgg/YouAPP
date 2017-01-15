package me.bunny.kernel.mock;

import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.container._resource.JResourceURIParserService;
import me.bunny.kernel.jave.io.JURIPart;
import me.bunny.kernel.jave.support._package.JDefaultMethodMeta;

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
