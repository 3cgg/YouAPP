package me.bunny.kernel.jave.xml.node;

import java.util.List;

public interface JNameGetter extends JNodeGetter {
	
	public List<?> getNodesByName(String name);

}
