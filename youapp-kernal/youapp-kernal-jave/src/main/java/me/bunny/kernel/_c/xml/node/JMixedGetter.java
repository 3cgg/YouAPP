package me.bunny.kernel._c.xml.node;

import java.util.List;

public interface JMixedGetter extends JNodeGetter {
	
	/**
	 * 
	 * @param mixed  // "class:consume,tag:table"
	 * @return
	 */
	public List<?> getNodesByMixed(String mixed);

}
