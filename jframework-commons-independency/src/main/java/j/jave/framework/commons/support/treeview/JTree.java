package j.jave.framework.commons.support.treeview;

import j.jave.framework.commons.support.console.JRepresent;
import j.jave.framework.commons.utils.JAssert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class JTree implements JRepresent{

	/**
	 * tree view 
	 */
	private List<JTreeNode> treeNodes=new ArrayList<JTreeNode>();;
	
	private final Collection<? extends JTreeStrcture> treeStrctures;
	
	public JTree(Collection<? extends JTreeStrcture> treeStrctures){
		this.treeStrctures=treeStrctures;
	}
	
	public JTree get(){
		
		Map<String, JTreeStrcture> models=new HashMap<String, JTreeStrcture>();
		LinkedBlockingQueue<String> itemIndexQueue=new LinkedBlockingQueue<String>(treeStrctures.size());
		
		for (Iterator<? extends JTreeStrcture> iterator = treeStrctures.iterator(); iterator.hasNext();) {
			JTreeStrcture treeStrcture =  iterator.next();
			models.put(treeStrcture.getId(), treeStrcture);
			itemIndexQueue.add(treeStrcture.getId());
		}
		
		Map<String, JTreeNode> treeNodeMap=new HashMap<String, JTreeNode>();
		// avoid consume computer resource
		int maxCycle=itemIndexQueue.size()*(itemIndexQueue.size()+1)/2;
		
		int cycleCount=0;
		while(!itemIndexQueue.isEmpty()&&cycleCount<maxCycle){
			cycleCount++;
			JTreeStrcture item = models.get(itemIndexQueue.poll());
			
			JTreeNode treeNode=treeNodeMap.get(item.getId());
			if(treeNode==null){
				//new initialization
				JTreeNodeConfig treeNodeConfig=new JTreeNodeConfig();
				
				if(item.isText()){
					// text
					JDefaultTreeText defaultTreeText=new JDefaultTreeText(item);
					treeNode=defaultTreeText;
					defaultTreeText.setTextConfig(treeNodeConfig);
					treeNodeConfig.setTreeNodeRepresentStrategy(new JDefaultNodeRepresentStrategy(defaultTreeText));
				}
				else{
					// element  , capability of holding other element of text
					JDefaultTreeElement defaultTreeElement=new JDefaultTreeElement(item);
					treeNode=defaultTreeElement;
					defaultTreeElement.setElementConfig(treeNodeConfig);
					treeNodeConfig.setTreeNodeRepresentStrategy(new JDefaultNodeRepresentStrategy(defaultTreeElement));
				}
				
				treeNodeMap.put(item.getId(), treeNode);
				
				if(item.getParentId()==null){
					//top level as without parent id , processed , removed
					treeNodeConfig.setLevel(0);
					treeNodeConfig.addPathPart(item.getId()); 
					treeNodeConfig.setOffset(treeNodes.size()+1);
					treeNodes.add(treeNode);
				}
				else{
					// put another chance to process this as with parent id
					itemIndexQueue.add(item.getId()) ;
				}
			}
			else{
				//all items should have parent id
				String parentId=item.getParentId();
				JTreeNode parentTreeNode=treeNodeMap.get(parentId);
				
				JAssert.state(parentTreeNode!=null, "the parent ["+item.getParentId()+"] of model with id ["+item.getId()+"] does not exists.");
				JAssert.state(JTreeElement.class.isInstance(parentTreeNode), "text ["+item.getId()+"] cannot be included in another text (parent) ["+parentId+"]");
				JTreeElement parentTreeElement=((JTreeElement)parentTreeNode);
				JTreeNode thisTreeNode=treeNodeMap.get(item.getId());
				JAssert.state(parentTreeElement!=thisTreeNode, "the parent ["+item.getParentId()+"] of model with id ["+item.getId()+"] is the as self.");
				
				JAssert.state(parentTreeElement.getParent()!=thisTreeNode, "the model ["+item.getParentId()+"] and model ["+item.getId()+"] references each other..");
				
				thisTreeNode.getNodeConfig().setLevel(parentTreeElement.getNodeConfig().getLevel()+1);
				
				thisTreeNode.getNodeConfig().addPathParts(parentTreeElement.getNodeConfig().getPath());
				thisTreeNode.getNodeConfig().addPathPart(item.getId());
				
				thisTreeNode.getNodeConfig().setOffset(parentTreeElement.elementCount()+1);
				
				parentTreeElement.addChildren(thisTreeNode);
				thisTreeNode.addParent(parentTreeElement);
			}
		}
		
		return this;
	}

	@Override
	public String represent() {
		
		StringBuffer stringBuffer=new StringBuffer();
		
		for (Iterator<JTreeNode> iterator = treeNodes.iterator(); iterator.hasNext();) {
			JTreeNode treeNode =  iterator.next();
			JTreeWalker treeWalker=new JTreeWalker(treeNode);
			while(treeWalker.hasNext()){
				JTreeNode walkerTreeNode=treeWalker.nextNode();
				JTreeStrcture treeStrcture= walkerTreeNode.getData();
				
				stringBuffer.append("\n"+walkerTreeNode.getNodeConfig().getTreeNodeRepresentStrategy().represent()
						+"    ----data::--- id->"+treeStrcture.getId()+"---parent-id->"+treeStrcture.getParentId()+"---istext->"+treeStrcture.isText());
			}
		}
		return stringBuffer.toString();
	}
	
	
	
}
