package j.jave.framework.unit.dev.test;

import j.jave.kernal.taskdriven.tasks.bill.ActivitiDemoTask;
import j.jave.kernal.taskdriven.tasks.bill.RecordBillTask;
import j.jave.kernal.taskdriven.tasks.bill.SearchBillTask;
import j.jave.kernal.taskdriven.tkdd.JTaskContext;
import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;
import j.jave.kernal.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

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
