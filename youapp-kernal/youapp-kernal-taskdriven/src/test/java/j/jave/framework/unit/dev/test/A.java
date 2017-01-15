package j.jave.framework.unit.dev.test;

import me.bunny.modular._p.taskdriven.tasks.bill.ActivitiDemoTask;
import me.bunny.modular._p.taskdriven.tasks.bill.RecordBillTask;
import me.bunny.modular._p.taskdriven.tasks.bill.SearchBillTask;
import me.bunny.modular._p.taskdriven.tkdd.JTaskContext;
import me.bunny.modular._p.taskdriven.tkdd.flow.JFlowContext;
import me.bunny.modular._p.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

public class A {

	
	public static void main(String[] args) throws Exception {

		JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
		
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
		JFlowContext  flowContext =new JFlowContext();
		simpleLinkedFlowImpl.start(flowContext);
		
	}
}
