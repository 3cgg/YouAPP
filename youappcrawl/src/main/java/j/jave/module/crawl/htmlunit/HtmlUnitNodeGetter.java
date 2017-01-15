package j.jave.module.crawl.htmlunit;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import me.bunny.kernel._c.xml.node.JClassNameGetter;
import me.bunny.kernel._c.xml.node.JIDGetter;
import me.bunny.kernel._c.xml.node.JMixedGetter;
import me.bunny.kernel._c.xml.node.JNameGetter;
import me.bunny.kernel._c.xml.node.JTagNameGetter;
import me.bunny.kernel._c.xml.node.JW3CStandardGetter;
import me.bunny.kernel._c.xml.node.JXPathGetter;

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
