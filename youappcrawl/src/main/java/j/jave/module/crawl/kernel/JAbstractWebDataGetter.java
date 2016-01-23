package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;

public abstract class JAbstractWebDataGetter  implements JWebDataGetter{

	protected boolean success=true;
	
	protected static final String[] empty={};
	
	protected Class<? extends JWebModel> webModelClass;
	
	public JAbstractWebDataGetter(Class<? extends JWebModel> webModelClass){
		this.webModelClass=webModelClass;
	}
	
	public JAbstractWebDataGetter(){
	}
	
	public void setWebModelClass(Class<? extends JWebModel> webModelClass) {
		this.webModelClass = webModelClass;
	}
	
}
