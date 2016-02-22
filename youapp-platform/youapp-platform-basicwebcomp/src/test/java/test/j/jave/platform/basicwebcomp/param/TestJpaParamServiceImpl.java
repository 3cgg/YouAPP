package test.j.jave.platform.basicwebcomp.param;

import j.jave.kernal.jave.random.JSimpleObjectPopulate;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.service.ParamService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="test.paramService.transation.jpa")
public class TestJpaParamServiceImpl{
	
	@Autowired
	private ParamService paramService;
	
	public TestJpaParamServiceImpl(){
		System.out.println("TestParamServiceImpl");
	}
	
	public void testJpa(){
		try{
			ServiceContext context=new ServiceContext();
			User user=new User();
			user.setId("SYSTEM-TEST");
			context.setUser(user);
			
			Param param=new Param();
			new JSimpleObjectPopulate().populate(param);
			param.setId(JUniqueUtils.unique().replaceAll("-", ""));
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
