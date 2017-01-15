package me.bunny.kernel._c.xml.node;

import java.util.List;

public interface JClassNameGetter extends JNodeGetter {

	public List<?> getNodesByClassName(String... className);
	
}
