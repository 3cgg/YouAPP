package j.jave.kernal.streaming.coordinator.rpc.worker;

import j.jave.kernal.streaming.coordinator.WorkerExecutingPathVal;
import j.jave.kernal.streaming.netty.controller.ControllerService;
import j.jave.kernal.streaming.netty.controller.IControllerImplementer;

public interface IWorkerService extends ControllerService , IControllerImplementer<WorkerService>{

	/**
	 * 
	 * @param executingPathVal
	 * @return
	 */
	boolean notifyWorkers(WorkerExecutingPathVal executingPathVal);
	
	
	
}
