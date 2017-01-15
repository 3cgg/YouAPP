package j.jave.kernal.streaming.coordinator;

import java.util.Map;

import me.bunny.kernel._c.model.JModel;

public class Task implements JModel {

	private String zkNode;
	
	private String id;
	
	private String workflowName;
	
	private Map<String, Object> params;

	public String getWorkflowName() {
		return workflowName;
	}

	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getZkNode() {
		return zkNode;
	}

	public void setZkNode(String zkNode) {
		this.zkNode = zkNode;
	}
	
}
