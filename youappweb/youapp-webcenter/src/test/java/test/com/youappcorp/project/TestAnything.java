package test.com.youappcorp.project;

import junit.framework.TestCase;
import me.bunny.kernel._c.json.JJSON;
import me.bunny.kernel._c.model.JPageable;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactoryManager;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.ServletHttpContext;
import j.jave.platform.webcomp.web.youappmvc.service.PageableService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestAnything extends TestCase{

	private JServiceFactoryManager serviceFactoryManager=JServiceFactoryManager.get();
	{
		serviceFactoryManager.registerAllServices();
	}
	protected JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	
	@Test
	public void testEnum(){
		ResponseModel responseModel=ResponseModel.newSuccess();
		responseModel.setData("success...");
		System.out.println(JJSON.get().formatObject(responseModel));
	}
	
	@Test
	public void testPageable(){
		
		PageableService pageableService= serviceHubDelegate.getService(this, PageableService.class);
		JPageable page=pageableService.parse(new ServletHttpContext());
		System.out.println("end : "+page);
	}
}
