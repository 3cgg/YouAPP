package test.com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.flow.JFlowContext;
import j.jave.kernal.taskdriven.tkdd.flow.JSimpleLinkedFlowImpl;

import org.junit.Test;

import com.youappcorp.template.ftl.Config;
import com.youappcorp.template.ftl.PreparedConfigTask;
import com.youappcorp.template.ftl.SingleModelExecutingTask;
import com.youappcorp.template.ftl.TemplateUtil;

public class TestRepo extends TestEventSupport {

	
	@Test
	public void testRepo(){
		try{
			JSimpleLinkedFlowImpl simpleLinkedFlowImpl=new JSimpleLinkedFlowImpl();
			
			PreparedConfigTask preparedConfigTask= new PreparedConfigTask();
			simpleLinkedFlowImpl.put(preparedConfigTask);
			
			SingleModelExecutingTask singleModelExecutingTask=new SingleModelExecutingTask();
			simpleLinkedFlowImpl.put(singleModelExecutingTask);
			
			JFlowContext  flowContext =new JFlowContext();
			Config config=new Config();
			
			TemplateUtil.setConfig(flowContext, config);
			
			simpleLinkedFlowImpl.start(flowContext);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
