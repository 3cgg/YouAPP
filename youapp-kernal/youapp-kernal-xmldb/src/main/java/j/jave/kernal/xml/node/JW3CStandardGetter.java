package j.jave.kernal.xml.node;

import j.jave.kernal.xml.util.JNodeWalker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class JW3CStandardGetter implements JIDGetter, JNameGetter, JTagNameGetter,
JXPathGetter, JClassNameGetter, JMixedGetter {

	private Node node;
	
	public JW3CStandardGetter(Node node) {
		this.node=node;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<?> getNodes(String... keyValues) {
		List nodes=new ArrayList();
		for (int i = 0; i < keyValues.length; i++) {
			nodes.addAll(getNodesByMixed(keyValues[i]));
		}
		return nodes;
	}

	private String getProtocol(String protocol,String... keyValues){
		for (int i = 0; i < keyValues.length; i++) {
			String keyValue=keyValues[i];
			if(keyValue!=null&&keyValue.startsWith(protocol)){
				keyValues[i]=null;
				return keyValue;
			}
		}
		return null;
	}
	
	private String[] enhaceFilter(String... keyValues){
		String [] filters=new String[keyValues.length];
		int count=0;
		
		String keyValue=getProtocol(XPATH_PROTOCOL, keyValues);
		if(keyValue!=null){
			filters[count++]=keyValue;
		}
		keyValue=getProtocol(ID_PROTOCOL, keyValues);
		if(keyValue!=null){
			filters[count++]=keyValue;
		}
		keyValue=getProtocol(TAG_PROTOCOL, keyValues);
		if(keyValue!=null){
			filters[count++]=keyValue;
		}
		keyValue=getProtocol(NAME_PROTOCOL, keyValues);
		if(keyValue!=null){
			filters[count++]=keyValue;
		}
		keyValue=getProtocol(CLASS_PROTOCOL, keyValues);
		if(keyValue!=null){
			filters[count++]=keyValue;
		}
		keyValue=getProtocol(MIXED_PROTOCOL, keyValues);
		if(keyValue!=null){
			filters[count++]=keyValue;
		}
		
		for (int i = 0; i < keyValues.length; i++) {
			String kV=keyValues[i];
			if(kV!=null){
				filters[count++]=kV;
			}
		}
		return filters;
	}
	
	
	private List<?> getNode(String keyValue){
		String protocol=keyValue.substring(0, keyValue.indexOf(":"));
		String value=keyValue.substring(keyValue.indexOf(":")+1);
		if(XPATH_PROTOCOL.equals(protocol)){
			return getNodesByXPath(value);
		}
		else if(TAG_PROTOCOL.equals(protocol)){
			return getNodesByTagName(value);
		}
		else if(NAME_PROTOCOL.equals(protocol)){
			return getNodesByName(value);
		}
		else if(CLASS_PROTOCOL.equals(protocol)){
			return getNodesByClassName(value);
		}
		else if(ID_PROTOCOL.equals(protocol)){
			return getNodesById(value);
		}
		return Collections.EMPTY_LIST;
	}
	
	@Override
	public List<?> getNodesByMixed(String mixed) {
		if(mixed==null||mixed.trim().length()==0) return Collections.EMPTY_LIST;
		
		List<Object> nodes=null;
		String[] keyValues=enhaceFilter(mixed.split(","));
		for(int i=0;i<keyValues.length;i++){
			String keyValue=keyValues[i];
			if(nodes==null){
				nodes=new ArrayList<Object>();
				List<?> tempNodes=getNode(keyValue);
				nodes.addAll(tempNodes);
			}
			else{
				// do filter
				for (Iterator<?> iterator = nodes.iterator(); iterator.hasNext();) {
					Node node = (Node) iterator.next();
					String protocol=keyValue.substring(0, keyValue.indexOf(":"));
					String value=keyValue.substring(keyValue.indexOf(":")+1);
					if((protocol==null||protocol.trim().length()==0)
							||
							value==null||value.trim().length()==0
							){
						continue;
					}
					//trim
					protocol=protocol.trim();
					value=value.trim();
					
					String nodeValueOnProtocol="";
					if(JNodeGetter.TAG_PROTOCOL.equals(protocol)){
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
						if("style".equalsIgnoreCase(protocol)){
							nodeValueOnProtocol=nodeValueOnProtocol.replaceAll(" ", "");
							nodeValueOnProtocol=nodeValueOnProtocol.replaceAll(":", "|");
							if(!nodeValueOnProtocol.endsWith(";")){
								nodeValueOnProtocol=nodeValueOnProtocol+";";
							}
							
							if(!value.endsWith(";")){
								nodeValueOnProtocol=nodeValueOnProtocol+";";
							}
						}
						else{
							nodeValueOnProtocol=" "+nodeValueOnProtocol.trim()+" ";
							value=" "+value+" ";
						}
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
	public List<?> getNodesByClassName(String... className) {
		JNodeWalker nodeWalker=new JNodeWalker(node);
		List<Node> nodes=new ArrayList<Node>();
		while(nodeWalker.hasNext()){
			Node node=nodeWalker.nextNode();
			Node attrNode= getAttrNode(node, "class");
			if(attrNode!=null){
				String nodeValue=" "+attrNode.getNodeValue().trim()+" ";
				boolean fullMatch=true;
				for(int i=0;i<className.length;i++){
					if(!nodeValue.contains(" "+className[i].trim()+" ")){
						fullMatch=false;
						break;
					}
				}
				if(fullMatch){
					nodes.add(node);
				}
			}
		}
		return nodes;
	}

	@Override
	public List<?> getNodesByXPath(String path) {
		
		XPathFactory factory = XPathFactory.newInstance();
        XPath xpath = factory.newXPath();
        List<Node> nodes=new ArrayList<Node>();
        try{
        	NodeList nodeList = (NodeList) xpath.evaluate(path, node,
                    XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                nodes.add(nodeList.item(i));
            }
        }catch(Exception e){
        }
		return nodes;
	}

	@Override
	public List<?> getNodesByTagName(String tagName) {
		JNodeWalker nodeWalker=new JNodeWalker(node);
		List<Node> nodes=new ArrayList<Node>();
		while(nodeWalker.hasNext()){
			Node node=nodeWalker.nextNode();
			if(tagName.equals(node.getNodeName())){
				nodes.add(node);
			}
		}
		return nodes;
	}

	@Override
	public List<?> getNodesByName(String name) {
		JNodeWalker nodeWalker=new JNodeWalker(node);
		List<Node> nodes=new ArrayList<Node>();
		while(nodeWalker.hasNext()){
			Node node=nodeWalker.nextNode();
			Node attrNode= getAttrNode(node, "name");
			if(attrNode!=null){
				if(name.equals(attrNode.getNodeValue())){
					nodes.add(node);
				}
			}
		}
		return nodes;
	}

	@Override
	public List<?> getNodesById(String id) {
		JNodeWalker nodeWalker=new JNodeWalker(node);
		List<Node> nodes=new ArrayList<Node>();
		while(nodeWalker.hasNext()){
			Node node=nodeWalker.nextNode();
			Node idAttrNode= getAttrNode(node, "id");
			if(idAttrNode!=null){
				if(id.equals(idAttrNode.getNodeValue())){
					nodes.add(node);
				}
			}
		}
		return nodes;
	}
	
	private Node getAttrNode(Node node,String attrName){
		Node attrNode= node.getAttributes().getNamedItem(attrName);
		if(attrNode==null){
			attrNode= node.getAttributes().getNamedItem(attrName.toLowerCase());
		}
		if(attrNode==null){
			attrNode= node.getAttributes().getNamedItem(attrName.toUpperCase());
		}
		return attrNode;
	}
	
	

}
