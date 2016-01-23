package j.jave.framework.commons.support.treeview;

public class JDefaultTreeText implements JTreeText {
	
	private JTreeElement parent;
	
	private JTreeNodeConfig textConfig;
	
	private final JTreeStrcture data;
	
	public JDefaultTreeText(JTreeStrcture data){
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

	public JTreeNodeConfig getTextConfig() {
		return textConfig;
	}

	public void setTextConfig(JTreeNodeConfig textConfig) {
		this.textConfig = textConfig;
	}

	@Override
	public void addParent(JTreeElement parent) {
		this.parent=parent;
	}

	@Override
	public JTreeNodeConfig getNodeConfig() {
		return textConfig;
	}
	
	
	
}
