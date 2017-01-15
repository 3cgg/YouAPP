package me.bunny.kernel._c.support.treeview;

import java.util.List;

/**
 * as a node, can contain any other elements as children.
 * @author JIAZJ
 *
 */
public interface JTreeElement extends JTreeNode{
	
	/**
	 * append an element.
	 * @param treeNode
	 */
	public void addChildren(JTreeNode treeNode) ;
	
	/**
	 *the element count in the node.
	 * @return
	 */
	public int elementCount();
	
	/**
	 * return all children element.
	 * @return
	 */
	public List<JTreeNode> getChildren();
}
