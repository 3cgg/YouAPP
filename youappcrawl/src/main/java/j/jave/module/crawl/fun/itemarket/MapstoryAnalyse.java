package j.jave.module.crawl.fun.itemarket;

import j.jave.kernal.xml.util.JNodeWrapper;
import j.jave.kernal.xml.util.JNodeWrapperWalker;
import j.jave.module.crawl.def.JWebModel;
import j.jave.module.crawl.kernel.JNodeAnalyse;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;

public class MapstoryAnalyse implements JNodeAnalyse {

	private static final String TABLE="table";
	
	private static final String TBODY="tbody";
	
	private static final String THEAD="thead";
	
	private static final String TFOOT="tfoot";
	
	private static final String TR="tr";
	
	private static final String TH="th";
	
	private static final String TD="td";
	
	private static final String SPAN="span";
	
	private static final String FONT="font";
	
	private static final String DIV="div";
	
	protected Node node;
	
	protected Class<? extends JWebModel> webModelClass;
	
	protected JNodeWrapper nodeWrapper;
	
	@Override
	public List<JWebModel> analyse(Node node,
			Class<? extends JWebModel> webModelClass) {
		nodeWrapper=new JNodeWrapper(node,true);
		List<JWebModel> webModels=new ArrayList<JWebModel>();
		MapstoryModel mapstoryModel=new MapstoryModel();
		JNodeWrapperWalker nodeWrapperWalker=new JNodeWrapperWalker(nodeWrapper);
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			Node thisNode=thisNodeWrapper.getNode();
			String nodeName=thisNode.getNodeName();
			
			if(TD.equalsIgnoreCase(nodeName)){
				if(isIcon(thisNodeWrapper)){
					
					JNodeWrapper nameNodeWrapper=getNextTR(thisNode, 2);
					mapstoryModel.setName(nameNodeWrapper.getNode().getTextContent());
					
					JNodeWrapper priceNodeWrapper=getNextTR(thisNode, 3);
					mapstoryModel.setPrice(priceNodeWrapper.getNode().getTextContent());
					
					nodeWrapperWalker.skipChildren();
				}
			}
			
			if(SPAN.equalsIgnoreCase(nodeName)
					&&matchAttr(thisNode, "class", "mp")
					){
				String text= thisNode.getTextContent();
				if(text.indexOf("线")!=-1){
					mapstoryModel.setLocation(text);
					nodeWrapperWalker.skipChildren();
				}
				if(text.indexOf("的雇用商人")!=-1){
					mapstoryModel.setSeller(text);
					nodeWrapperWalker.skipChildren();
				}
			}
			
			if(thisNode.getTextContent().trim().equals("物品类别：")
					||thisNode.getTextContent().trim().equals("物品类别:")
					){
				JNodeWrapper valueNodeWrapper= getNextValue(nodeWrapperWalker, thisNodeWrapper.getParent());
				if(valueNodeWrapper!=null){
					mapstoryModel.setGoodType(valueNodeWrapper.getNode().getTextContent());
				}
				
				String time=getTime(thisNodeWrapper.getParent().getParent().getNode());
				mapstoryModel.setTime(time);
			}
			
			if(thisNode.getTextContent().trim().equals("职业")
					){
				JNodeWrapper valueNodeWrapper= getNextValue(nodeWrapperWalker, thisNodeWrapper.getParent());
				if(valueNodeWrapper!=null){
					mapstoryModel.setGoodOccupation(valueNodeWrapper.getNode().getTextContent());
				}
			}
			
			if(thisNode.getTextContent().trim().equals("等级")
					){
				JNodeWrapper valueNodeWrapper= getNextValue(nodeWrapperWalker, thisNodeWrapper.getParent());
				if(valueNodeWrapper!=null){
					mapstoryModel.setGoodLevel(valueNodeWrapper.getNode().getTextContent());
				}
			}
			
			if(TD.equalsIgnoreCase(nodeName)){
				String text=thisNode.getTextContent().trim();
				if(text.startsWith("基础属性")){
					mapstoryModel.setGoodProperties(text);
					nodeWrapperWalker.skipChildren();
				}
			}
			
			if(FONT.equalsIgnoreCase(nodeName)){
				String text=thisNode.getTextContent().trim();
				if(text.startsWith("A级(史诗物品)")){
					mapstoryModel.setGoodUpgradeLevel(text);
					nodeWrapperWalker.skipChildren();
				}
				
			}
			
			if(SPAN.equalsIgnoreCase(nodeName)
					&&(
							thisNode.getTextContent().trim().equals("潜能属性条数:")
							||thisNode.getTextContent().trim().equals("潜能属性条数：")	
							)
					){
				JNodeWrapper valueNodeWrapper= getNextValue(nodeWrapperWalker, thisNodeWrapper.getParent());
				if(valueNodeWrapper!=null){
					mapstoryModel.setGoodUpgradePropertiesNum(valueNodeWrapper.getNode().getTextContent());
				}
				
				valueNodeWrapper= getNextDIV(nodeWrapperWalker, thisNodeWrapper.getParent());
				if(valueNodeWrapper!=null){
					mapstoryModel.setGoodUpgradeProperties(valueNodeWrapper.getNode().getTextContent());
				}
			}
		}
		webModels.add(mapstoryModel);
		return webModels;
	}
	
	private JNodeWrapper getNextValue(JNodeWrapperWalker nodeWrapperWalker,JNodeWrapper parent){
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper peekNodeWrapper= nodeWrapperWalker.peekNextNode();
			if(peekNodeWrapper==parent){
				return null;
			}
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			Node thisNode=thisNodeWrapper.getNode();
			String nodeName=thisNode.getNodeName();
			if(SPAN.equalsIgnoreCase(nodeName)
					&&matchAttr(thisNode, "class", "text2")
					){
				return thisNodeWrapper;
			}
		}
		return null;
		
	}
	
	private String getTime(Node root ){
		JNodeWrapperWalker nodeWrapperWalker=new JNodeWrapperWalker(new JNodeWrapper(root,true));
		int count=0;
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			Node thisNode=thisNodeWrapper.getNode();
			String nodeName=thisNode.getNodeName();
			if(DIV.equalsIgnoreCase(nodeName)){
				count++;
				nodeWrapperWalker.skipChildren();
			}
			if(count==2){
				return thisNode.getTextContent();
			}
		}
		return null;
	}
	
	private JNodeWrapper getNextTR(Node root,int trIndex){
		JNodeWrapperWalker nodeWrapperWalker=new JNodeWrapperWalker(new JNodeWrapper(root,true));
		int count=0;
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			Node thisNode=thisNodeWrapper.getNode();
			String nodeName=thisNode.getNodeName();
			if(TR.equalsIgnoreCase(nodeName)){
				count++;
				nodeWrapperWalker.skipChildren();
			}
			if(count==trIndex){
				return thisNodeWrapper;
			}
		}
		return null;
	}
	
	
	
	private JNodeWrapper getNextDIV(JNodeWrapperWalker nodeWrapperWalker,JNodeWrapper parent){
		while(nodeWrapperWalker.hasNext()){
			JNodeWrapper peekNodeWrapper= nodeWrapperWalker.peekNextNode();
			if(peekNodeWrapper==parent){
				return null;
			}
			JNodeWrapper thisNodeWrapper=nodeWrapperWalker.nextNode();
			Node thisNode=thisNodeWrapper.getNode();
			String nodeName=thisNode.getNodeName();
			if(DIV.equalsIgnoreCase(nodeName)){
				return thisNodeWrapper;
			}
		}
		return null;
		
	}
	
	private boolean matchAttr(Node node, String key,String... values){
		Node attrNode=node.getAttributes().getNamedItem(key);
		if(attrNode==null) return false;
		boolean match=true;
		for (int i = 0; i < values.length; i++) {
			String value=values[i];
			if(attrNode.getNodeValue().indexOf(value)==-1){
				match=false;
			}
		} 
		return match;
	}
	
	private boolean isIcon(JNodeWrapper nodeWrapper){
		Node thisNode=nodeWrapper.getNode();
		String nodeName=thisNode.getNodeName();
		if(TD.equalsIgnoreCase(nodeName)){
			Node onmouseover= thisNode.getAttributes().getNamedItem("onmouseover");
			if(onmouseover!=null){
				String nodeValue=onmouseover.getNodeValue();
				return nodeValue.indexOf("showToolTip")!=-1
						&&nodeValue.indexOf("物品介绍")!=-1;
			}
		}
		return false;
	}

	@Override
	public JWebModel analyse(Node node, JWebModel webModel) {
		return null;
	}

}
