package j.jave.framework.unit.dev.test;

import me.bunny.kernel.taskdriven.tasks.bill.ActivitiDemoTask;
import me.bunny.kernel.taskdriven.tasks.bill.RecordBillTask;
import me.bunny.kernel.taskdriven.tasks.bill.SearchBillTask;
import me.bunny.kernel.taskdriven.tkdd.JTaskContext;
import me.bunny.kernel.taskdriven.tkdd.flow.JFlowContext;
import me.bunny.kernel.taskdriven.tkdd.flow.JSimpleFlowGroupImpl;

public class B {

	
	public static void main(String[] args) throws Exception {

		JFlowContext flowContext =new JFlowContext();
		
		JSimpleFlowGroupImpl simpleLinkedFlowImpl=new JSimpleFlowGroupImpl(flowContext);
		
		RecordBillTask recordBillTask= new RecordBillTask(new JTaskContext());
		simpleLinkedFlowImpl.put(recordBillTask);
		
		SearchBillTask searchBillTask=new SearchBillTask(new JTaskContext());
		simpleLinkedFlowImpl.put(searchBillTask);
		
		ActivitiDemoTask activitiDemoTask=new ActivitiDemoTask(new JTaskContext());
		simpleLinkedFlowImpl.put(activitiDemoTask);
		
		recordBillTask= new RecordBillTask(new JTaskContext());
		simpleLinkedFlowImpl.put(recordBillTask);
		
		searchBillTask=new SearchBillTask(new JTaskContext());
		simpleLinkedFlowImpl.put(searchBillTask);
		
		activitiDemoTask=new ActivitiDemoTask(new JTaskContext());
		simpleLinkedFlowImpl.put(activitiDemoTask);
		
		simpleLinkedFlowImpl.start();
		
	}
}
