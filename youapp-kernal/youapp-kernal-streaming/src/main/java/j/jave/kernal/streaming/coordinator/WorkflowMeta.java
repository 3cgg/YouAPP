package j.jave.kernal.streaming.coordinator;

import me.bunny.kernel._c.model.JModel;

public class WorkflowMeta implements JModel {

	/**
	 * the unique id / znode path
	 */
	private String unique;
	
	/**
	 * the workflow name
	 */
	private String name;
	
	/**
	 * the max available executing count
	 */
	private long count;
	
	private NodeData nodeData;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public NodeData getNodeData() {
		return nodeData;
	}

	public void setNodeData(NodeData nodeData) {
		this.nodeData = nodeData;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public String getUnique() {
		return unique;
	}

	public void setUnique(String unique) {
		this.unique = unique;
	}
	
	
}
