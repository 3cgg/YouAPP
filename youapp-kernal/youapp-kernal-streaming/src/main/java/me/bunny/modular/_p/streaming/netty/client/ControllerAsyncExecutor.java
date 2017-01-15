package me.bunny.modular._p.streaming.netty.client;

public class ControllerAsyncExecutor {
	
//	private static final JLogger LOGGER=JLoggerFactory.getLogger(ControllerAsyncExecutor.class);

	private ControllerCallPromisePipeline callPromisePipeline=new ControllerCallPromisePipeline(null);

	private static ControllerAsyncExecutor asyncExecutor=new ControllerAsyncExecutor();
	
	public static ControllerAsyncExecutor get(){
		return asyncExecutor;
	}
	
	void execute(ControllerCallPromise<Object> callPromise){
		ControllerCallPromiseEvent callPromiseEvent=new ControllerCallPromiseEvent(callPromise, callPromise);
		callPromisePipeline.addAPPEvent(callPromiseEvent);
	}
	
}
