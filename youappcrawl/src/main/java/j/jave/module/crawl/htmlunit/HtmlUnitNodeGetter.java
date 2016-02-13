package j.jave.module.crawl.htmlunit;

import j.jave.kernal.jave.xml.node.JClassNameGetter;
import j.jave.kernal.jave.xml.node.JIDGetter;
import j.jave.kernal.jave.xml.node.JMixedGetter;
import j.jave.kernal.jave.xml.node.JNameGetter;
import j.jave.kernal.jave.xml.node.JTagNameGetter;
import j.jave.kernal.jave.xml.node.JW3CStandardGetter;
import j.jave.kernal.jave.xml.node.JXPathGetter;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HtmlUnitNodeGetter implements JClassNameGetter, JNameGetter,
		JTagNameGetter, JXPathGetter ,JIDGetter,JMixedGetter {
	
	private HtmlPage htmlPage;
	
	public HtmlUnitNodeGetter(HtmlPage htmlPage){
		this.htmlPage=htmlPage;
	}

	@Override
	public List<?> getNodesByXPath(String xpath) {
		return htmlPage.getByXPath(xpath);
	}

	@Override
	public List<?> getNodesByTagName(String tagName) {
		return htmlPage.getElementsByTagName(tagName);
	}

	@Override
	public List<?> getNodesByName(String name) {
		return htmlPage.getElementsByName(name);
	}

	@Override
	public List<?> getNodesByClassName(String... className) {
		List<?> nodes=getNodesByTagName("html");
		JW3CStandardGetter standardGetter=new JW3CStandardGetter((Node) nodes.get(0));
		return standardGetter.getNodesByClassName(className);
	}

	@Override
	public List<?> getNodesByMixed(String mixed) {
		List<?> nodes=getNodesByTagName("html");
		JW3CStandardGetter standardGetter=new JW3CStandardGetter((Node) nodes.get(0));
		return standardGetter.getNodesByMixed(mixed);
	}

	@Override
	public List<?> getNodes(String... keyValues) {
		List<?> nodes=getNodesByTagName("html");
		JW3CStandardGetter standardGetter=new JW3CStandardGetter((Node) nodes.get(0));
		return standardGetter.getNodes(keyValues);
	}

	@Override
	public List<?> getNodesById(String id) {
		List<Object> nodes=new ArrayList<Object>();
		DomElement domElement= htmlPage.getElementById(id);
		if(domElement!=null){
			nodes.add(domElement);
		}
		return nodes;
	}

	

	

}
