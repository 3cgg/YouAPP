package j.jave.kernal.streaming.coordinator;

import java.util.Map;

import j.jave.kernal.streaming.ConfigNames;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.utils.JUniqueUtils;

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
		leaderNodeMeta.setWorkflowStatusMs(getInt(ConfigNames.STREAMING_LEADER_WORKFLOW_STATUS_MS,3000));
		leaderNodeMeta.setWorkflowStatusMs(getInt(ConfigNames.STREAMING_LEADER_WORKFLOW_TO_ONLINE_MS,10000));
		
		return leaderNodeMeta;
	}

}
