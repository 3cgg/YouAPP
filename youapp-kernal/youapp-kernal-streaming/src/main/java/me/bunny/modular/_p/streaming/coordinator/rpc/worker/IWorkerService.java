package me.bunny.modular._p.streaming.coordinator.rpc.worker;

import me.bunny.modular._p.streaming.coordinator.WorkerExecutingPathVal;
import me.bunny.modular._p.streaming.netty.controller.ControllerService;
import me.bunny.modular._p.streaming.netty.controller.IControllerImplementer;

public interface IWorkerService extends ControllerService , IControllerImplementer<WorkerService>{

	/**
	 * 
	 * @param executingPathVal
	 * @return
	 */
	boolean notifyWorkers(WorkerExecutingPathVal executingPathVal);
	
	
	
}
