package j.jave.kernal.streaming.coordinator;

import j.jave.kernal.JConfiguration;

public class WorkerNodeMetaGetter extends NodeMetaGetter<WorkerNodeMeta> {

	public WorkerNodeMetaGetter(JConfiguration configuration) {
		super(configuration);
	}

	@Override
	public WorkerNodeMeta nodeMeta() {
		WorkerNodeMeta workerNodeMeta=new WorkerNodeMeta();
		_setBasicOnNodeMeta(workerNodeMeta);
		return workerNodeMeta;
	}

}
