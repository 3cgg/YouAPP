package j.jave.kernal.streaming.coordinator;

public class LeaderNodeMeta extends NodeMeta {

	private final boolean leader=true;

	public boolean isLeader() {
		return leader;
	}
	
	
}
