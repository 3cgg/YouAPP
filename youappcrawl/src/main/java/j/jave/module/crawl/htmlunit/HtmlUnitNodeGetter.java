package j.jave.module.crawl.htmlunit;

import j.jave.module.crawl.def.JWebConstants;
import j.jave.module.crawl.node.JClassNameGetter;
import j.jave.module.crawl.node.JIDGetter;
import j.jave.module.crawl.node.JMixedGetter;
import j.jave.module.crawl.node.JNameGetter;
import j.jave.module.crawl.node.JNodeGetterUtil;
import j.jave.module.crawl.node.JTagNameGetter;
import j.jave.module.crawl.node.JXPathGetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
	public List<?> getNodesByClassName(String className) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<?> getNodesByMixed(String mixed) {
		if(mixed==null||mixed.trim().length()==0) return Collections.EMPTY_LIST;
		
		List<Object> nodes=null;
		String[] keyValues=mixed.split(",");
		for(int i=0;i<keyValues.length;i++){
			String keyValue=keyValues[i];
			if(nodes==null){
				nodes=new ArrayList<Object>();
				List<?> tempNodes=getNodes(keyValue);
				nodes.addAll(tempNodes);
			}
			else{
				// do filter
				for (Iterator<?> iterator = nodes.iterator(); iterator.hasNext();) {
					Node node = (Node) iterator.next();
					String protocol=keyValue.substring(0, keyValue.indexOf(":"));
					String value=keyValue.substring(keyValue.indexOf(":")+1);
					String nodeValueOnProtocol=JWebConstants.EMPTY;
					if(JNodeGetterUtil.TAG_PROTOCOL.equals(protocol)){
						nodeValueOnProtocol=node.getNodeName();
					}
					else{
						Node attrNode=node.getAttributes().getNamedItem(protocol);
						if(attrNode!=null){
							nodeValueOnProtocol=attrNode.getNodeValue();
						}
						else{
							iterator.remove();
							continue;
						}
					}
					
					if(value.length()>0){
						nodeValueOnProtocol=JWebConstants.SPACE+nodeValueOnProtocol.trim()+JWebConstants.SPACE;
						value=JWebConstants.SPACE+value.trim()+JWebConstants.SPACE;
						if(!nodeValueOnProtocol.contains(value)){
							iterator.remove();
						}
					}
					
//					else if(JNodeGetterUtil.NAME_PROTOCOL.equals(protocol)){
//						Node attrNode=node.getAttributes().getNamedItem("name");
//						if(attrNode!=null){
//							nodeValueOnProtocol=attrNode.getNodeValue();
//						}
//					}
//					else if(JNodeGetterUtil.ID_PROTOCOL.equals(protocol)){
//						nodeValueOnProtocol=node.getAttributes().getNamedItem("id").getNodeValue();
//					}
//					else if(JNodeGetterUtil.CLASS_PROTOCOL.equals(protocol)){
//						nodeValueOnProtocol=node.getAttributes().getNamedItem("class").getNodeValue();
//					}
				}
			}
		}
		return nodes==null? Collections.EMPTY_LIST:nodes;
	}

	@Override
	public List<?> getNodes(String keyValue) {
		
		if(keyValue.contains(",")){
			//mixed
			return getNodesByMixed(keyValue);
		}
		String protocol=keyValue.substring(0, keyValue.indexOf(":"));
		String value=keyValue.substring(keyValue.indexOf(":")+1);
		
		if(JNodeGetterUtil.XPATH_PROTOCOL.equals(protocol)){
			return getNodesByXPath(value);
		}
		else if(JNodeGetterUtil.TAG_PROTOCOL.equals(protocol)){
			return getNodesByTagName(value);
		}
		else if(JNodeGetterUtil.NAME_PROTOCOL.equals(protocol)){
			return getNodesByName(value);
		}
		else if(JNodeGetterUtil.CLASS_PROTOCOL.equals(protocol)){
			return getNodesByClassName(value);
		}
		else if(JNodeGetterUtil.ID_PROTOCOL.equals(protocol)){
			return getNodesById(value);
		}
		return Collections.EMPTY_LIST;
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
