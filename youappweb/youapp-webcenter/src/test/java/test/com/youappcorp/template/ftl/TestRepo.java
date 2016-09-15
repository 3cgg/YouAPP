package test.com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;
import j.jave.kernal.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

import org.junit.Test;

import com.youappcorp.template.ftl.RepoTask;

public class TestRepo extends TestEventSupport {

	
	@Test
	public void testRepo(){
		try{
			JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
			
			RepoTask repoTask= new RepoTask();
			simpleLinkedFlowImpl.put(repoTask);
			
			JFlowContext  flowContext =new JFlowContext();
			simpleLinkedFlowImpl.start(flowContext);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
