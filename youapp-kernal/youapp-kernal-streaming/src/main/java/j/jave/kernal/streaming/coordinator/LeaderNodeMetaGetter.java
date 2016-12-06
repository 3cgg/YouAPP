package j.jave.kernal.streaming.coordinator;

import java.util.Map;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.kernal.streaming.ConfigNames;

public class LeaderNodeMetaGetter extends NodeMetaGetter<LeaderNodeMeta> {

	public LeaderNodeMetaGetter(JConfiguration configuration,Map conf) {
		super(configuration,conf);
	}

	@Override
	public LeaderNodeMeta nodeMeta() {
		LeaderNodeMeta leaderNodeMeta=new LeaderNodeMeta();
		_setBasicOnNodeMeta(leaderNodeMeta);
		leaderNodeMeta.setPort(getInt(ConfigNames.STREAMING_LEADER_NETTY_SERVER_PORT, 8080));
		leaderNodeMeta.setZkThreadCount(getInt(ConfigNames.STREAMING_LEADER_ZOOKEEPER_THREAD_COUNT,9));
		leaderNodeMeta.setLogThreadCount(getInt(ConfigNames.STREAMING_LEADER_LOGGING_THREAD_COUNT, 9));
		leaderNodeMeta.setTaskRepoPath(getString(ConfigNames.STREAMING_LEADER_TASKREPO_ZNODE, "/tasks-"+JUniqueUtils.SEQUECE+"-repo"));
		return leaderNodeMeta;
	}

}
