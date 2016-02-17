package test.j.jave.platform.basicwebcomp.param;

import j.jave.kernal.jave.random.JObjectPopulate;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.service.ParamService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestParam {
	
	@Autowired
	private ParamService paramService;
	
	@Test
	public void testParam(){
		try{
			ServiceContext context=new ServiceContext();
			User user=new User();
			user.setId("SYSTEM-TEST");
			context.setUser(user);
			
			Param param=new Param();
			new JObjectPopulate(param).populate();
			
			paramService.saveParam(context, param);
			
			Param dbParam=paramService.getById(context, param.getId());
			dbParam.setDescription("JIA.ZHONG.JIN");
			paramService.updateParam(context, dbParam);
			
			paramService.delete(context, param.getId());
			dbParam=paramService.getById(context, param.getId());
			System.out.println(dbParam.getDeleted());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
}
