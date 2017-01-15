package me.bunny.kernel.taskdriven.tkdd.flow;

import java.util.concurrent.ConcurrentLinkedQueue;

import me.bunny.kernel.taskdriven.tkdd.JTask;

/**
 * Tasks here are executing based on the the order of appending.
 * @author J
 *
 */
public class JSimpleLinkedFlowImpl extends  JAbstractFlow implements JFlow {

	private ConcurrentLinkedQueue<JTask> taksQueue=new ConcurrentLinkedQueue<JTask>();
	
	public void put(JTask task){
		taksQueue.add(task);
	}
	
	private final JFlowContext  flowContext;
	
	public JSimpleLinkedFlowImpl(JFlowContext  flowContext){
		this.flowContext=flowContext;
	}
	
	public JSimpleLinkedFlowImpl(){
		flowContext=null;
	}
	
	public Object start()  throws Exception  {
		return start(flowContext);
	}

	@Override
	public Object start(JFlowContext  flowContext)  throws Exception {
		if(JFlowContext.get()==null){
			JFlowContext.set(flowContext);
		}
		while(taksQueue.size()>0){
			taksQueue.poll().start(flowContext);
		}
		return true;
	}
	
}
