package j.jave.module.crawl.node;

import java.util.List;


public interface JNodeGetter {
	
	/**
	 * 
	 * @param keyValue // "tag:table,id:j-area-filter"
	 * @return
	 */
	public List<?> getNodes(String keyValue);
	
}
