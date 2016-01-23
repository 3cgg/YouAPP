package j.jave.framework.commons.xmldb.utils;

import org.dom4j.Element;

public interface JElementFilter {

	/**
	 * true , the element is saved ; false, the element is dropped.
	 * @param element
	 * @return
	 */
	public abstract boolean filterElement(Element element);
	
}
