package j.jave.kernal.jave.support.treeview;


public interface JTreeNode {

	public void addParent(JTreeElement parent) ;
	
	public JTreeElement getParent();
	
	public JTreeNodeConfig getNodeConfig();
	
	public JTreeStrcture getData();
	
}
