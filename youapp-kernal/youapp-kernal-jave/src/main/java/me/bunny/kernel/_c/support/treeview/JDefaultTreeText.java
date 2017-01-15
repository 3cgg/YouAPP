package me.bunny.kernel._c.support.treeview;

public class JDefaultTreeText implements JTreeText {
	
	private JTreeElement parent;
	
	private JTreeNodeMeta textConfig;
	
	private final JSimpleTreeStrcture data;
	
	public JDefaultTreeText(JSimpleTreeStrcture data){
		this.data=data;
	}
	
	public JSimpleTreeStrcture getData() {
		return data;
	}

	public JTreeElement getParent() {
		return parent;
	}

	public void setParent(JTreeElement parent) {
		this.parent = parent;
	}

	public JTreeNodeMeta getTextConfig() {
		return textConfig;
	}

	public void setTextConfig(JTreeNodeMeta textConfig) {
		this.textConfig = textConfig;
	}

	@Override
	public void addParent(JTreeElement parent) {
		this.parent=parent;
	}

	@Override
	public JTreeNodeMeta getNodeMeta() {
		return textConfig;
	}
	
	
	
}
