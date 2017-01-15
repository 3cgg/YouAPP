package me.bunny.kernel.taskdriven.tkdd.flow;

public abstract class JAbstractFlowGroup implements JFlowGroup{

protected boolean root=false;
	
	protected String name;

	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
