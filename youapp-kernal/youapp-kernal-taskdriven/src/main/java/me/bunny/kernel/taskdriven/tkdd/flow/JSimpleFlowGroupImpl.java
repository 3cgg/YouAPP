package me.bunny.kernel.taskdriven.tkdd.flow;

import java.util.concurrent.ConcurrentLinkedQueue;

import me.bunny.kernel.taskdriven.tkdd.JNotifyStart;
import me.bunny.kernel.taskdriven.tkdd.JTask;

/**
 * Tasks here are executing based on the the order of appending.
 * @author J
 *
 */
public class JSimpleFlowGroupImpl extends  JAbstractFlowGroup implements JFlowGroup {

	private ConcurrentLinkedQueue<JNotifyStart> flowsQueue=new ConcurrentLinkedQueue<JNotifyStart>();
	
	public void put(JTask task){
		JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
		simpleLinkedFlowImpl.put(task);
		flowsQueue.add(simpleLinkedFlowImpl);
	}
	
	private JFlowContext  flowContext;
	
	public JSimpleFlowGroupImpl(JFlowContext  flowContext) {
		this.flowContext=flowContext;
	}
	
	public JSimpleFlowGroupImpl() {
	}
	
	@Override
	public void put(JFlow flow) {
		flowsQueue.add(flow);
	}
	
	public Object start()  throws Exception {
		return start(flowContext);
	}
	
	@Override
	public Object start(JFlowContext flowContext)  throws Exception {
		while(flowsQueue.size()>0){
			flowsQueue.poll().start(flowContext);
		}
		return true;
	}

	@Override
	public void put(JNotifyStart notifyStart) {
		flowsQueue.add(notifyStart);
	}
	
}
