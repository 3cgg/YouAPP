package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JCrawlExecution {
	
	private final Class<? extends JWebModel> webModelClass;
	private final JCrawlContext crawlContext;
	
	public JCrawlExecution(Class<? extends JWebModel> webModelClass,JCrawlContext crawlContext){
		this.webModelClass=webModelClass;
		this.crawlContext=crawlContext;
	}
	
	public List<JWebModel> execute(){
		List<JWebModel> webModels=new ArrayList<JWebModel>();
		List<JWebDataGetter> webDataGetters= JWebDataGetterUtil.get(webModelClass, crawlContext);
		if(webDataGetters!=null&&!webDataGetters.isEmpty()){
			for (Iterator<JWebDataGetter> iterator = webDataGetters.iterator(); iterator
					.hasNext();) {
				JWebDataGetter webDataGetter =  iterator.next();
				try{
					webDataGetter.execute();
					if(webDataGetter.success()){
						webModels.addAll(webDataGetter.get());
						break;
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return webModels;
	}
	
}
