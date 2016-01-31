package j.jave.kernal.jave.support.treeview;

import java.util.List;


public interface JTreeElement extends JTreeNode{
	
	public void addChildren(JTreeNode treeNode) ;
	
	public int elementCount();
	
	public List<JTreeNode> getChildren();
}
