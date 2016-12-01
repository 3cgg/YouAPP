package j.jave.kernal.streaming.coordinator;

public class WorkerNodeMeta extends NodeMeta {

	private boolean leader=false;

	public boolean isLeader() {
		return leader;
	}
	
	public void setLeader(boolean leader) {
		this.leader = leader;
	}
	
}
