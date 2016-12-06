package j.jave.kernal.streaming.coordinator;

import java.util.Map;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.streaming.ConfigNames;

public class WorkerNodeMetaGetter extends NodeMetaGetter<WorkerNodeMeta> {

	public WorkerNodeMetaGetter(JConfiguration configuration,Map conf) {
		super(configuration,conf);
	}

	@Override
	public WorkerNodeMeta nodeMeta() {
		WorkerNodeMeta workerNodeMeta=new WorkerNodeMeta();
		_setBasicOnNodeMeta(workerNodeMeta);
		workerNodeMeta.setPort(getInt(ConfigNames.STREAMING_WORKER_NETTY_SERVER_PORT, 8080));
		workerNodeMeta.setZkThreadCount(getInt(ConfigNames.STREAMING_WORKER_ZOOKEEPER_THREAD_COUNT,9));
		workerNodeMeta.setLogThreadCount(getInt(ConfigNames.STREAMING_WORKER_LOGGING_THREAD_COUNT, 9));
		workerNodeMeta.setHeartBeatTimeMs(getInt(ConfigNames.STREAMING_WORKER_HEARTBEATS_TIME_MS, 1000));
		return workerNodeMeta;
	}

}
