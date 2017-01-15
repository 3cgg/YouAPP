package me.bunny.kernel._c.support.treeview;

import java.util.LinkedList;
import java.util.List;

/**
 * path like: <br/>
 * 1<br/>
 *	1.1  <br/>
 *	1.1.1<br/>
 *	1.2  <br/>
 *	2    <br/>
 *	2.1  <br/>
 *	2.2  <br/>
 *	3    <br/>
 *	3.1  <br/>
 *	4    <br/>
 *	5    
 * @author J
 *
 */
public class JTreeWalker {

	private JTreeNode currentNode;
	
	private LinkedList<JTreeNode> treeNodes;

	public JTreeWalker(JTreeNode treeNode) {
		treeNodes=new LinkedList<JTreeNode>();
		treeNodes.add(treeNode);
	}

	public JTreeNode nextNode() {

		// if no next node return null
		if (!hasNext()) {
			return null;
		}
		// pop the next node off of the link and push all of its children onto
		// the stack
		currentNode = treeNodes.poll();
		
		if(JTreeElement.class.isInstance(currentNode)){
			
			JTreeElement curEle=(JTreeElement) currentNode;
			List<JTreeNode> currentChildren= curEle.getChildren();
			for(int i=currentChildren.size()-1;i>=0;i--){
				//reverse put 
				treeNodes.addFirst(currentChildren.get(i));
			}
			
		}

		return currentNode;
	}

	public boolean hasNext() {
		return (treeNodes.size() > 0);
	}

}
