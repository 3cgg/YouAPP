package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;

import java.util.ArrayList;
import java.util.List;

public abstract class JAbstractWebDataGetter  implements JWebDataGetter{

	protected boolean success=true;
	
	protected static final String[] empty={};
	
	protected Class<? extends JWebModel> webModelClass;
	
	protected JCrawlContext crawlContext;
	
	protected List<JWebModel>  webModels=new ArrayList<JWebModel>();
	
	protected Exception exception; 
	
	public JAbstractWebDataGetter(){
	}
	
	@Override
	public void setWebModelClass(Class<? extends JWebModel> webModelClass) {
		this.webModelClass = webModelClass;
	}
	
	@Override
	public void setCrawlContext(JCrawlContext crawlContext) {
		this.crawlContext=crawlContext;
	}
	
	@Override
	public boolean success() {
		return success;
	}
}
