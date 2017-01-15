package me.bunny.kernel._c.xml.dom4j.util;

import org.dom4j.Element;

public class JTagNameFilter implements JElementFilter {

	private String tagName;
	
	public  JTagNameFilter(String tagName) {
		this.tagName=tagName;
	}
	
	@Override
	public boolean filterElement(Element element) {
		return tagName.equals(element.getName());
	}

}
