package test.com.youappcorp.project.param;

import me.bunny.app._c._web.core.service.DefaultServiceContext;
import me.bunny.app._c._web.core.service.ServiceContext;
import me.bunny.kernel._c.support.random.JSimpleObjectRandomBinder;
import me.bunny.kernel._c.utils.JUniqueUtils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.codetable.model.ParamCode;
import com.youappcorp.project.codetable.model.ParamType;
import com.youappcorp.project.codetable.service.CodeTableService;

@Service
public class TestJpaParamServiceImpl{
	
	@Autowired
	private CodeTableService paramService;
	
	public TestJpaParamServiceImpl(){
		System.out.println("TestParamServiceImpl");
	}
	
	public void saveParamTypeAndCode() throws BusinessException{
		try{
			ServiceContext context=DefaultServiceContext.getDefaultServiceContext();
			
			ParamType paramType=new ParamType();
			new JSimpleObjectRandomBinder().bind(paramType);
			paramType.setId(JUniqueUtils.unique().replaceAll("-", ""));
			paramType.setCode("SEX-TEST-"+new Date().getTime());
			ParamCode paramCode=new ParamCode();
			new JSimpleObjectRandomBinder().bind(paramCode);
			paramCode.setId(JUniqueUtils.unique().replaceAll("-", ""));
			paramCode.setCode("F-TEST-2");
			paramCode.setName("女");
			paramService.saveParam( paramType,paramCode);
			
//			paramCode.setDescription("JIA.ZHONG.JIN");
//			paramService.updateParam(context, paramType,paramCode);
			
			
			System.out.println("count : ");
		}catch(Exception e){
			throw new BusinessException(e);
		}
		
	}
	
}
