package me.bunny.modular._p.streaming.coordinator;

public abstract class RestrictLockPath {

	public static String workerLockPath(String workflowName,int workerId){
		return 
				CoordinatorPaths.BASE_PATH
				+"/worker-register-sync-lock/"+workflowName+"/"+workerId;
	}
	
}
