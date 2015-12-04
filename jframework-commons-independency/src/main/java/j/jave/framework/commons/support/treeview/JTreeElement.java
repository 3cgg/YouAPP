package j.jave.framework.commons.support.treeview;

import java.util.List;


public interface JTreeElement extends JTreeNode{
	
	public void addChildren(JTreeNode treeNode) ;
	
	public int elementCount();
	
	public List<JTreeNode> getChildren();
}
