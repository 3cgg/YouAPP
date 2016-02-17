package j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JAssert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class JTree{

	private static JLogger LOGGER=JLoggerFactory.getLogger(JTree.class);
	
	public static enum Action{
		DROP,REPORT
	}
	
	private final Action howto;
	
	/**
	 * tree view 
	 */
	private List<JTreeNode> treeNodes;
	
	private final Collection<? extends JSimpleTreeStrcture> treeStrctures;
	
	/**
	 * convenience to {@link #JTree(Collection, Action.REPORT))}
	 * @param treeStrctures
	 */
	public JTree(Collection<? extends JSimpleTreeStrcture> treeStrctures){
		this.treeStrctures=treeStrctures;
		this.howto=Action.REPORT;
	}
	
	/**
	 * @param treeStrctures
	 * @param howto set whether to report error while processing the tree structure.
	 */
	public JTree(Collection<? extends JSimpleTreeStrcture> treeStrctures,Action howto){
		this.treeStrctures=treeStrctures;
		this.howto=howto;
	}
	
	public synchronized JTree get(){
		if(treeNodes!=null) return this;
		treeNodes=new ArrayList<JTreeNode>();
		Map<String, JSimpleTreeStrcture> models=new HashMap<String, JSimpleTreeStrcture>();
		LinkedBlockingQueue<String> itemIndexQueue=new LinkedBlockingQueue<String>(treeStrctures.size());
		Set<String> parentNodes=new HashSet<String>();
		
		for (Iterator<? extends JSimpleTreeStrcture> iterator = treeStrctures.iterator(); iterator.hasNext();) {
			JSimpleTreeStrcture treeStrcture =  iterator.next();
			models.put(treeStrcture.getId(), treeStrcture);
			itemIndexQueue.add(treeStrcture.getId());
			parentNodes.add(treeStrcture.getParentId());
		}
		
		Map<String, JTreeNode> treeNodeMap=new HashMap<String, JTreeNode>();
		// avoid consume computer resource
		int maxCycle=itemIndexQueue.size()*(itemIndexQueue.size()+1)/2;
		
		int cycleCount=0;
		while(!itemIndexQueue.isEmpty()&&cycleCount<maxCycle){
			cycleCount++;
			JSimpleTreeStrcture item = models.get(itemIndexQueue.poll());
			
			JTreeNode treeNode=treeNodeMap.get(item.getId());
			if(treeNode==null){
				//new initialization
				JTreeNodeMeta treeNodeConfig=new JTreeNodeMeta();
				boolean isText=false;
				if(JAdvancedTreeStrcture.class.isInstance(item)){
					isText=((JAdvancedTreeStrcture)item).isText();
				}
				else{
					isText=!parentNodes.contains(item.getId());
				}
				if(isText){
					// text
					JDefaultTreeText defaultTreeText=new JDefaultTreeText(item);
					treeNode=defaultTreeText;
					defaultTreeText.setTextConfig(treeNodeConfig);
				}
				else{
					// element  , capability of holding other element of text
					JDefaultTreeElement defaultTreeElement=new JDefaultTreeElement(item);
					treeNode=defaultTreeElement;
					defaultTreeElement.setElementConfig(treeNodeConfig);
				}
				
				treeNodeMap.put(item.getId(), treeNode);
				
				if(item.getParentId()==null){
					//top level as without parent id , processed , removed
					treeNodeConfig.setLevel(1);
					treeNodeConfig.addPathPart(item.getId()); 
					treeNodeConfig.setAbsolutePath("/"+item.getId());
					treeNodeConfig.setOffset(treeNodes.size()+1);
					treeNodeConfig.setGlobalOffset(String.valueOf(treeNodeConfig.getOffset()));
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
				
				boolean expected=parentTreeNode!=null;
				if(!expected){
					String message="the parent ["+item.getParentId()+"] of model with id ["+item.getId()+"] does not exists.";
					if(howto==Action.REPORT){
						JAssert.state(expected, message);
					}
					else if(howto==Action.DROP){
						LOGGER.info(message);
						continue;
					}
				}
				
				expected=JTreeElement.class.isInstance(parentTreeNode);
				if(!expected){
					String message="text ["+item.getId()+"] cannot be included in another text (parent) ["+parentId+"]";
					if(howto==Action.REPORT){
						JAssert.state(expected, message);
					}
					else if(howto==Action.DROP){
						LOGGER.info(message);
						continue;
					}
				}
				
//				JAssert.state(parentTreeNode!=null, "the parent ["+item.getParentId()+"] of model with id ["+item.getId()+"] does not exists.");
//				JAssert.state(JTreeElement.class.isInstance(parentTreeNode), "text ["+item.getId()+"] cannot be included in another text (parent) ["+parentId+"]");
				JTreeElement parentTreeElement=((JTreeElement)parentTreeNode);
				JTreeNode thisTreeNode=treeNodeMap.get(item.getId());
//				JAssert.state(parentTreeElement!=thisTreeNode, "the parent ["+item.getParentId()+"] of model with id ["+item.getId()+"] is the as self.");
				expected=parentTreeElement!=thisTreeNode;
				if(!expected){
					String message="the parent ["+item.getParentId()+"] of model with id ["+item.getId()+"] is the as self.";
					if(howto==Action.REPORT){
						JAssert.state(expected, message);
					}
					else if(howto==Action.DROP){
						LOGGER.info(message);
						continue;
					}
				}
				
//				JAssert.state(parentTreeElement.getParent()!=thisTreeNode, "the model ["+item.getParentId()+"] and model ["+item.getId()+"] references each other..");
				expected=parentTreeElement.getParent()!=thisTreeNode;
				if(!expected){
					String message="the model ["+item.getParentId()+"] and model ["+item.getId()+"] references each other.";
					if(howto==Action.REPORT){
						JAssert.state(expected, message);
					}
					else if(howto==Action.DROP){
						LOGGER.info(message);
						continue;
					}
				}
				thisTreeNode.getNodeMeta().setLevel(parentTreeElement.getNodeMeta().getLevel()+1);
				thisTreeNode.getNodeMeta().addPathParts(parentTreeElement.getNodeMeta().getPath());
				thisTreeNode.getNodeMeta().addPathPart(item.getId());
				thisTreeNode.getNodeMeta().setAbsolutePath(parentTreeElement.getNodeMeta().getAbsolutePath()+"/"+item.getId());
				thisTreeNode.getNodeMeta().setOffset(parentTreeElement.elementCount()+1);
				thisTreeNode.getNodeMeta().setGlobalOffset(parentTreeElement.getNodeMeta().getGlobalOffset()+"."+thisTreeNode.getNodeMeta().getOffset());
				parentTreeElement.addChildren(thisTreeNode);
				thisTreeNode.addParent(parentTreeElement);
			}
		}
		return this;
	}
	
	public List<JTreeNode> getTreeNodes() {
		return treeNodes;
	}
	
	
}
