package me.bunny.kernel._c.xml.dom4j.util;

import org.dom4j.Element;

public abstract class JElementHandler {

	boolean noExist=true;
	
	public abstract boolean handleElement(Element element);
	
}
