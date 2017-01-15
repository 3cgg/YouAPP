package me.bunny.kernel.taskdriven.tkdd;

import me.bunny.kernel.taskdriven.tkdd.flow.JFlow;
import me.bunny.kernel.taskdriven.tkdd.flow.JFlowContext;

/**
 * the flow or task running wrapper, may contain task flow definition, the context of executing environment , also some monitors.
 * @author J
 *
 */
public class JTaskRunner {

	private JFlow flow;
	
	private JFlowContext flowContext;
	
	public JFlow getFlow() {
		return flow;
	}

	public void setFlow(JFlow flow) {
		this.flow = flow;
	}

	public JFlowContext getFlowContext() {
		return flowContext;
	}

	public void setFlowContext(JFlowContext flowContext) {
		this.flowContext = flowContext;
	}

	public Object run() throws Exception {
		return flow.start(flowContext);
	}
	
	
}
