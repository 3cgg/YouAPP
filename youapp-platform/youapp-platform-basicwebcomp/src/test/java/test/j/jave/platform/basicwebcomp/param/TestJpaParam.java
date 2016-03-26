package test.j.jave.platform.basicwebcomp.param;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.param.jpa.ParamCodeJPARepo;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.model.ParamCriteria;
import j.jave.platform.basicwebcomp.param.repo.ParamCodeRepo;
import j.jave.platform.basicwebcomp.param.service.ParamService;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestJpaParam {
	
	@Autowired
	private TestJpaParamServiceImpl paramService;
	
	@Autowired
	private ParamService paramS;
	
	@Test
	public void aram(){
		paramService.testJpa();
	}
	
	@Test
	public void testG(){
		paramS.getCodeTableCacheModels(DefaultServiceContext.getDefaultServiceContext());
	}
	
	@Test
	public void testPPage(){
//		ServiceContext context=new ServiceContext();
//		User user=new User();
//		user.setId("SYSTEM-TEST");
//		context.setUser(user);
//		
//		ParamCriteria pagination=new ParamCriteria();
//		
//		JPage<ParamCode> params= paramS.getsByPage(context, pagination);
//		
//		JPage<ParamCode> paramPage= paramS.getParamsByNameByPage(context, pagination,"a");
//		ParamCode param=paramPage.getContent().get(0);
//		long count=paramS.countParam(context, param);
//		System.out.println("count : "+count);
//		
//		List<ParamCode> dbParams=paramS.allParams(context, param);
//		System.out.println(dbParams.size());
//		System.out.println(paramPage.getTotalPageNumber());
	}
	
	
	public static void main(String[] args) {
		
		Method[] methods=ParamCodeJPARepo.class.getMethods();
		
		Method[] methods2=ParamCodeRepo.class.getMethods();
		
		
		System.out.println("END");
		
		
	}
}
