package j.jave.kernal.jave.support.treeview;

import j.jave.kernal.jave.model.JModel;

import java.util.ArrayList;
import java.util.List;

public class JNodeTreeView implements JModel{

	private String id;
	
	private String name;
	
	private JTreeNodeMeta meta;
	
	private List<JNodeTreeView> children=new ArrayList<JNodeTreeView>();
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public JTreeNodeMeta getMeta() {
		return meta;
	}

	public void setMeta(JTreeNodeMeta meta) {
		this.meta = meta;
	}

	public List<JNodeTreeView> getChildren() {
		return children;
	}

	public void setChildren(List<JNodeTreeView> children) {
		this.children = children;
	}
}
