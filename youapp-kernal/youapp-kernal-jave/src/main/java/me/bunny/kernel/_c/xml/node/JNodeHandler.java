package me.bunny.kernel._c.xml.node;

import org.w3c.dom.Node;

public abstract class JNodeHandler {

	boolean noExist=true;
	
	public abstract boolean handleNode(Node node);
	
}
