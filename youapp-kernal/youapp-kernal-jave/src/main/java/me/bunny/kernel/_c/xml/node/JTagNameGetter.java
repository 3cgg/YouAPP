package me.bunny.kernel._c.xml.node;

import java.util.List;

public interface JTagNameGetter extends JNodeGetter {

	public List<?> getNodesByTagName(String tagName);

}
