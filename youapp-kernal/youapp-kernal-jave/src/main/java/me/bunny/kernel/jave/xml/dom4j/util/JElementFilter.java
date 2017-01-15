package me.bunny.kernel.jave.xml.dom4j.util;

import org.dom4j.Element;

public interface JElementFilter {

	/**
	 * true , the element is saved ; false, the element is dropped.
	 * @param element
	 * @return
	 */
	public abstract boolean filterElement(Element element);
	
}
