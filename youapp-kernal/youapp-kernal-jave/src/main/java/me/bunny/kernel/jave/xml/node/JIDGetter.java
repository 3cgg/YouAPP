package me.bunny.kernel.jave.xml.node;

import java.util.List;

public interface JIDGetter extends JNodeGetter {
	
	public List<?> getNodesById(String id);

}
