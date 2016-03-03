package test.j.jave.platform.basicwebcomp.param;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.param.jpa.ParamJPARepo;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.model.ParamCriteria;
import j.jave.platform.basicwebcomp.param.repo.ParamRepo;
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
	public void testPPage(){
		ServiceContext context=new ServiceContext();
		User user=new User();
		user.setId("SYSTEM-TEST");
		context.setUser(user);
		
		ParamCriteria pagination=new ParamCriteria();
		
		JPage<Param> params= paramS.getsByPage(context, pagination);
		
		JPage<Param> paramPage= paramS.getParamsByNameByPage(context, pagination,"a");
		Param param=paramPage.getContent().get(0);
		long count=paramS.countParam(context, param);
		System.out.println("count : "+count);
		
		List<Param> dbParams=paramS.allParams(context, param);
		System.out.println(dbParams.size());
		System.out.println(paramPage.getTotalPageNumber());
	}
	
	
	public static void main(String[] args) {
		
		Method[] methods=ParamJPARepo.class.getMethods();
		
		Method[] methods2=ParamRepo.class.getMethods();
		
		
		System.out.println("END");
		
		
	}
}
