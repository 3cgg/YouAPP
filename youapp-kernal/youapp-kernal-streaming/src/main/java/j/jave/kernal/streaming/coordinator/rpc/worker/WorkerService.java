package j.jave.kernal.streaming.coordinator.rpc.worker;

import j.jave.kernal.streaming.coordinator.NodeWorker;
import j.jave.kernal.streaming.coordinator.NodeWorkers;
import j.jave.kernal.streaming.coordinator.WorkerExecutingPathVal;
import j.jave.kernal.streaming.netty.controller.ControllerSupport;

public class WorkerService extends ControllerSupport<WorkerService>
implements IWorkerService{
	
	@Override
	public String getControllerServiceName() {
		return "WorkerService";
	}

	@Override
	public boolean notifyWorkers(WorkerExecutingPathVal executingPathVal) {
		try{
			int nodeWorkerId=executingPathVal.getWorkerPathVal().getId();
			NodeWorker nodeWorker=NodeWorkers.get(nodeWorkerId);
			nodeWorker.wakeup(executingPathVal);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
}
