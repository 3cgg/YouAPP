package j.jave.module.crawl.node;

import java.util.List;

public interface JTagNameGetter extends JNodeGetter {

	public List<?> getNodesByTagName(String tagName);

}
