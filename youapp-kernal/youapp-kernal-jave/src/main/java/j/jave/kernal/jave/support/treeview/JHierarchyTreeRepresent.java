package j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.support.console.JRepresent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JHierarchyTreeRepresent implements
		JRepresent {

	private final JTree tree;
	
	public JHierarchyTreeRepresent(JTree tree){
		this.tree=tree;
	}
	
	private void processTreeNode(JTreeNode treeNode,JNodeTreeView parent){
		
		if(JTreeElement.class.isInstance(treeNode)){
			JTreeElement treeElement=(JTreeElement) treeNode;
			List<JTreeNode> treeNodes=treeElement.getChildren();
			for(int i=0;i<treeNodes.size();i++){
				JNodeTreeView thisNV= new JNodeTreeView();
				parent.getChildren().add(thisNV);
				processTreeNode(treeNodes.get(i), thisNV);
			}
		}
		JTreeStrcture treeStrcture= treeNode.getData();
		parent.setId(treeStrcture.getId());
		parent.setName(treeStrcture.getName());
		parent.setMeta(treeNode.getNodeMeta());
	}
	
	@Override
	public String represent() {
		List<JNodeTreeView> nodeTreeViews=new ArrayList<JNodeTreeView>();
		for (Iterator<JTreeNode> iterator = tree.getTreeNodes().iterator(); iterator.hasNext();) {
			JTreeNode treeNode =  iterator.next();
			JNodeTreeView nodeTreeView=new JNodeTreeView();
			processTreeNode(treeNode, nodeTreeView);
			nodeTreeViews.add(nodeTreeView);
		}
		return JJSON.get().formatObject(nodeTreeViews);
	}

}
