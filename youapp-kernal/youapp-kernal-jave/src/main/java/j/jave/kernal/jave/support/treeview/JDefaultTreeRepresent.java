package j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.support.console.JRepresent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JDefaultTreeRepresent implements
		JRepresent {

	private final JTree tree;
	
	public JDefaultTreeRepresent(JTree tree){
		this.tree=tree;
	}
	
	@Override
	public String represent() {
		List<JNodeTreeView> nodeTreeViews=new ArrayList<JNodeTreeView>();
		for (Iterator<JTreeNode> iterator = tree.getTreeNodes().iterator(); iterator.hasNext();) {
			JTreeNode treeNode =  iterator.next();
			JTreeWalker treeWalker=new JTreeWalker(treeNode);
			while(treeWalker.hasNext()){
				JTreeNode walkerTreeNode=treeWalker.nextNode();
				JTreeStrcture treeStrcture= walkerTreeNode.getData();
				JNodeTreeView nodeTreeView=new JNodeTreeView();
				nodeTreeView.setId(treeStrcture.getId());
				nodeTreeView.setName(treeStrcture.getName());
				nodeTreeView.setMeta(walkerTreeNode.getNodeMeta());
				nodeTreeViews.add(nodeTreeView);
			}
		}
		return JJSON.get().formatObject(nodeTreeViews);
	}

}
