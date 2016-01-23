package j.jave.module.crawl.node;

import java.util.List;


public interface JXPathGetter extends JNodeGetter {

	public List<?> getNodesByXPath(String xpath);
	
}
