package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;

import java.util.List;

public interface JWebDataGetter {
	
	public List<JWebModel> get();
	
	public void setCrawlContext(JCrawlContext crawlContext);
	
	public void setWebModelClass(Class<? extends JWebModel> webModelClass);
	
	public boolean success();
	
	public void execute();
	
}
