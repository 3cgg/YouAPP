package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.def.JWebModelDefProperties;
import j.jave.module.crawl.def.JWebNodeModel;

import java.util.ArrayList;
import java.util.List;

public class JWebDataGetterUtil {
	
	public static List<JWebDataGetter> get(Class<? extends JWebModel> webModelClass,JCrawlContext crawlContext){
		try{
			JWebNodeModel webNodeModel= webModelClass.getAnnotation(JWebNodeModel.class);
			List<JWebDataGetter> webDataGetters=new ArrayList<JWebDataGetter>();
			
			if(webNodeModel!=null){
				String[] getters=webNodeModel.getter();
				for (int i = 0; i < getters.length; i++) {
					String getter=getters[i];
					if(JWebModelDefProperties.WEB_MODEL_GETTER_SCOPE.equals(getter)){
						JScopeWebDataGetter scopeWebDataGetter=new JScopeWebDataGetter();
						scopeWebDataGetter.setWebModelClass(webModelClass);
						scopeWebDataGetter.setCrawlContext(crawlContext);
						webDataGetters.add(scopeWebDataGetter);
					}
					else if(JWebModelDefProperties.WEB_MODEL_GETTER_XPATH.equals(getter)){
						JXPathWebDataGetter pathWebDataGetter=new JXPathWebDataGetter(crawlContext);
						pathWebDataGetter.setCrawlContext(crawlContext);
						webDataGetters.add(pathWebDataGetter);
					}
				}
			}
			
			return webDataGetters;
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	
}
