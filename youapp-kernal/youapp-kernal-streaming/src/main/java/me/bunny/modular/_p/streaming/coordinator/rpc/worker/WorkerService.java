package me.bunny.modular._p.streaming.coordinator.rpc.worker;

import me.bunny.modular._p.streaming.coordinator.NodeWorker;
import me.bunny.modular._p.streaming.coordinator.NodeWorkers;
import me.bunny.modular._p.streaming.coordinator.WorkerExecutingPathVal;
import me.bunny.modular._p.streaming.netty.controller.ControllerSupport;
import me.bunny.modular._p.streaming.netty.controller.JRequestMapping;

@JRequestMapping(path="/workerservice")
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
