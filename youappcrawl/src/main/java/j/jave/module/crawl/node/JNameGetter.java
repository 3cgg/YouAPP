package j.jave.module.crawl.node;

import java.util.List;

public interface JNameGetter extends JNodeGetter {
	
	public List<?> getNodesByName(String name);

}
