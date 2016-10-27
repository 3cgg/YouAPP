package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.jave.model.JModel;

public class WorkflowMeta implements JModel {

	private String name;
	
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
	
}
