package j.jave.framework.unit.dev.test;

import j.jave.framework.tasks.bill.ActivitiDemoTask;
import j.jave.framework.tasks.bill.RecordBillTask;
import j.jave.framework.tasks.bill.SearchBillTask;
import j.jave.framework.tkdd.JTaskContext;

public class A {

	
	public static void main(String[] args) {
		
		RecordBillTask recordBillTask= new RecordBillTask(new JTaskContext(RecordBillTask.class));
		recordBillTask.start();
		
		SearchBillTask searchBillTask=new SearchBillTask(new JTaskContext(SearchBillTask.class));
		searchBillTask.start();
		
		ActivitiDemoTask activitiDemoTask=new ActivitiDemoTask(new JTaskContext(ActivitiDemoTask.class));
		activitiDemoTask.start();
		
		recordBillTask= new RecordBillTask(new JTaskContext(RecordBillTask.class));
		recordBillTask.start();
		
		searchBillTask=new SearchBillTask(new JTaskContext(SearchBillTask.class));
		searchBillTask.start();
		
		activitiDemoTask=new ActivitiDemoTask(new JTaskContext(ActivitiDemoTask.class));
		activitiDemoTask.start();
	}
}
