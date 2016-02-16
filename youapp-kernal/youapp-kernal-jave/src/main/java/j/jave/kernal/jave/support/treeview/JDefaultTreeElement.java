package j.jave.kernal.jave.support.treeview;

import java.util.ArrayList;
import java.util.List;

public class JDefaultTreeElement implements JTreeElement {

	private JTreeElement parent;
	
	private List<JTreeNode> children=new ArrayList<JTreeNode>();
	
	private JTreeNodeMeta elementConfig;
	
	private final JTreeStrcture data;
	
	public JDefaultTreeElement(JTreeStrcture data){
		this.data=data;
	}
	
	public JTreeStrcture getData() {
		return data;
	}



	public JTreeElement getParent() {
		return parent;
	}

	public void setParent(JTreeElement parent) {
		this.parent = parent;
	}

	public List<JTreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<JTreeNode> children) {
		this.children = children;
	}

	public JTreeNodeMeta getElementConfig() {
		return elementConfig;
	}

	public void setElementConfig(JTreeNodeMeta elementConfig) {
		this.elementConfig = elementConfig;
	}

	@Override
	public void addParent(JTreeElement parent) {
		this.parent=parent;
	}

	@Override
	public void addChildren(JTreeNode treeNode) {
		children.add(treeNode);
	}

	@Override
	public JTreeNodeMeta getNodeMeta() {
		return elementConfig;
	}

	@Override
	public int elementCount() {
		return this.children.size();
	}
	
	
	
	
	
}
