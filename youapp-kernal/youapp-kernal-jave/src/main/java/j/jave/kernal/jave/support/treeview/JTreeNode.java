package j.jave.kernal.jave.support.treeview;


/**
 * a node identifier
 * @author JIAZJ
 *
 */
public interface JTreeNode {

	/**
	 * make the argument as parent one.
	 * @param parent
	 */
	public void addParent(JTreeElement parent) ;
	
	/**
	 * return its parent element.
	 * @return
	 */
	public JTreeElement getParent();
	
	/**
	 * return the configuration info of the node.
	 * @return
	 */
	public JTreeNodeMeta getNodeMeta();
	
	/**
	 * return the data of the node.
	 * @return
	 */
	public JSimpleTreeStrcture getData();
	
}
