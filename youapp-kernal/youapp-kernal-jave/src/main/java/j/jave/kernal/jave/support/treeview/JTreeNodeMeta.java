package j.jave.kernal.jave.support.treeview;

import java.util.LinkedList;

public class JTreeNodeMeta {
	
	private int level;
	
	private LinkedList<String> path=new LinkedList<String>();
	
	private String absolutePath;
	/**
	 * offset under the same level
	 */
	private int offset;
	
	/**
	 * 1
	 * 1.1
	 * 1.1.1
	 * 1.2
	 * 1.2.1
	 * 2
	 * 2.1
	 * ...
	 */
	private String globalOffset;
	
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

	public String getGlobalOffset() {
		return globalOffset;
	}

	public void setGlobalOffset(String globalOffset) {
		this.globalOffset = globalOffset;
	}

	public String getAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	
}
