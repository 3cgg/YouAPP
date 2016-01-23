package j.jave.module.crawl.node;

import java.util.List;

public interface JIDGetter extends JNodeGetter {
	
	public List<?> getNodesById(String id);

}
