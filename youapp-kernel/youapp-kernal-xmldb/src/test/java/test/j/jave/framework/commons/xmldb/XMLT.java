package test.j.jave.framework.commons.xmldb;

import j.jave.framework.commons.xml.dom4j.JDom4jNodeGetter;
import j.jave.framework.commons.xml.dom4j.util.JXMLUtils;

import java.util.List;

public class XMLT {

	public static void main(String[] args) throws Exception {
		
		JDom4jNodeGetter dom4jNodeGetter=new JDom4jNodeGetter(JXMLUtils.loadDocument(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("html.xml")
				));
		
		List<?> element= dom4jNodeGetter.getNodesById("content-wrapper");
		
		List<?> elements1= dom4jNodeGetter.getNodesByName("description");
		
		List<?> elements2= dom4jNodeGetter.getNodesByTagName("a");
		
		List<?> elements3= dom4jNodeGetter.getNodesByClassName("line");
		
		
		
		
		System.out.println("e");
		
	}
}
