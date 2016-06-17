package test.com.youappcorp.project.param;

import j.jave.kernal.eventdriven.servicehub.JServiceFactoryManager;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.utils.JAssert;
import j.jave.platform.data.web.model.SimplePageCriteria;
import j.jave.platform.data.web.model.SimplePageRequest;
import j.jave.platform.webcomp.core.service.DefaultServiceContext;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.youappcorp.project.param.jpa.ParamCodeJPARepo;
import com.youappcorp.project.param.model.ParamCode;
import com.youappcorp.project.param.service.InternalParamCodeServiceImpl;
import com.youappcorp.project.param.service.ParamService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestJpaParam {
	
	static {
		JServiceFactoryManager.get().registerAllServices();
	}
	
	@Autowired
	private TestJpaParamServiceImpl paramService;
	
	@Autowired
	private ParamService paramS;
	
	@Autowired
	private InternalParamCodeServiceImpl internalParamCodeServiceImpl;
	
	
	@Test
	public void testCondition(){
		
		List<ParamCode> paramCodes= internalParamCodeServiceImpl.singleEntityQuery()
		.conditionDefault().ready().executeList();
		
		SimplePageCriteria simplePageCriteria=new SimplePageCriteria();
		simplePageCriteria.setPageNumber(0);
		simplePageCriteria.setPageSize(10);
		JPage<ParamCode> paramCodePage= internalParamCodeServiceImpl.singleEntityQuery()
				.conditionDefault().ready().executePageable(simplePageCriteria);
		
		JAssert.isNotNull(paramCodes);
		JAssert.isNotNull(paramCodePage);
	}
	
	@Test
	public void aram(){
		try{
			paramService.saveParamTypeAndCode();
			System.out.println(" save  success...");
		}catch(Exception e){
			e.printStackTrace();
		}
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
		
		Method[] methods2=ParamCodeJPARepo.class.getMethods();
		
		
		System.out.println("END");
		
		
	}
}
