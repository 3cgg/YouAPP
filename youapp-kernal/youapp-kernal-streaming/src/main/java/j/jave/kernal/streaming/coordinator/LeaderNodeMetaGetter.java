package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.JConfiguration;

public class LeaderNodeMetaGetter extends NodeMetaGetter<LeaderNodeMeta> {

	public LeaderNodeMetaGetter(JConfiguration configuration) {
		super(configuration);
	}

	@Override
	public LeaderNodeMeta nodeMeta() {
		LeaderNodeMeta leaderNodeMeta=new LeaderNodeMeta();
		_setBasicOnNodeMeta(leaderNodeMeta);
		return leaderNodeMeta;
	}

}
