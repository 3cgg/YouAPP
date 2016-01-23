package j.jave.module.crawl.node;

import java.util.List;

public interface JClassNameGetter extends JNodeGetter {

	public List<?> getNodesByClassName(String className);
	
}
