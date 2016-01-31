package j.jave.framework.commons.support.treeview;

import java.util.LinkedList;

public class JTreeNodeConfig {
	
	private int level;

	private JTreeNodeRepresentStrategy treeNodeRepresentStrategy;
	
	private LinkedList<String> path=new LinkedList<String>();
	
	/**
	 * offset under the same level
	 */
	private int offset;
	
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public JTreeNodeRepresentStrategy getTreeNodeRepresentStrategy() {
		return treeNodeRepresentStrategy;
	}

	public void setTreeNodeRepresentStrategy(
			JTreeNodeRepresentStrategy treeNodeRepresentStrategy) {
		this.treeNodeRepresentStrategy = treeNodeRepresentStrategy;
	}

	public LinkedList<String> getPath() {
		return path;
	}

	public void setPath(LinkedList<String> path) {
		this.path = path;
	}
	
	public void addPathParts(LinkedList<String> path){
		this.path.addAll(path);
	}
	
	public void addPathPart(String id){
		path.add(id);
	}
	
}
