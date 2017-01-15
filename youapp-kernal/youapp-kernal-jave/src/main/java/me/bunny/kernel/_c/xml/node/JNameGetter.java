package me.bunny.kernel._c.xml.node;

import java.util.List;

public interface JNameGetter extends JNodeGetter {
	
	public List<?> getNodesByName(String name);

}
