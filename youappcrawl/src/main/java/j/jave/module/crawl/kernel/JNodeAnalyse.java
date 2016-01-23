package j.jave.module.crawl.kernel;

import j.jave.module.crawl.def.JWebModel;

import java.util.List;

import org.w3c.dom.Node;

public interface JNodeAnalyse {
	
	public List<JWebModel> analyse(Node node,Class<? extends JWebModel> webModelClass);
	
	public JWebModel analyse(Node node,JWebModel webModel);
	
}
