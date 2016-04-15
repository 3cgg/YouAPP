package test.com.youappcorp.project.param;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.support.random.JSimpleObjectRandomBinder;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.param.model.ParamCode;
import com.youappcorp.project.param.model.ParamType;
import com.youappcorp.project.param.service.ParamService;

@Service(value="test.paramService.transation.jpa")
public class TestJpaParamServiceImpl{
	
	@Autowired
	private ParamService paramService;
	
	public TestJpaParamServiceImpl(){
		System.out.println("TestParamServiceImpl");
	}
	
	public void saveParamTypeAndCode() throws JServiceException{
		try{
			ServiceContext context=DefaultServiceContext.getDefaultServiceContext();
			
			ParamType paramType=new ParamType();
			new JSimpleObjectRandomBinder().bind(paramType);
			paramType.setId(JUniqueUtils.unique().replaceAll("-", ""));
			paramType.setCode("SEX");
			ParamCode paramCode=new ParamCode();
			new JSimpleObjectRandomBinder().bind(paramCode);
			paramCode.setId(JUniqueUtils.unique().replaceAll("-", ""));
			paramCode.setCode("F");
			paramCode.setName("女");
			paramService.saveParam(context, paramType,paramCode);
			
			paramCode.setDescription("JIA.ZHONG.JIN");
			paramService.updateParam(context, paramType,paramCode);
			
			
			System.out.println("count : ");
		}catch(Exception e){
			throw new JServiceException(e);
		}
		
	}
	
}
