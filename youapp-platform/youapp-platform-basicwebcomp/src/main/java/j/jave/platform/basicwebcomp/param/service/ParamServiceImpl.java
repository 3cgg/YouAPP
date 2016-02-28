/**
 * 
 */
package j.jave.platform.basicwebcomp.param.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.repo.ParamRepo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * parameter basic service.
 * @author J
 */
@Service(value="paramService.transation.jpa")
public class ParamServiceImpl extends ServiceSupport<Param> implements ParamService{

	public ParamServiceImpl(){
		System.out.println("----------ParamServiceImpl-------------------");
	}
	@Autowired
	private ParamRepo<?> paramMapper;
	
	@Override
	public void saveParam(ServiceContext context, Param param)
			throws JServiceException {
		
		if(exists(context, param)){
			throw new JServiceException("The param is existing, please change others.");
		}
		saveOnly(context, param);
	}

	@Override
	public void updateParam(ServiceContext context, Param param)
			throws JServiceException {
		if(exists(context, param)){
			throw new JServiceException("The param is existing, please change others.");
		}
		updateOnly(context, param);
	}

	@Override
	public Param getParamById(ServiceContext context, String id) {
		return getById(context, id);
	}

	@Override
	public JPage<Param> getParamsByPage(ServiceContext context, JPageable pagination) {
		return getsByPage(context, pagination);
	}

	@Override
	public Param getParamByFunctionIdAndCode(ServiceContext context,
			String functionId, String code) {
		return paramMapper.getParamByFunctionIdAndCode(functionId, code);
	}

	@Override
	public List<Param> getParamByFunctionId(ServiceContext context,
			String functionId) {
		return paramMapper.getParamByFunctionId(functionId);
	}

	@Override
	public JIPersist<?, Param> getRepo() {
		return paramMapper;
	}
	
	@Override
	public boolean exists(ServiceContext context, Param param) {
		
		if(JStringUtils.isNullOrEmpty(param.getFunctionId())){
			throw new IllegalArgumentException("Function id is null.");
		}
		if(JStringUtils.isNullOrEmpty(param.getCode())){
			throw new IllegalArgumentException("code is null.");
		}
		Param dbParam= getParamByFunctionIdAndCode(context, param.getFunctionId(), param.getCode());
		
		if(dbParam==null) return false;
		
		// status of inserting 
		if(JStringUtils.isNullOrEmpty(param.getId())){
			return dbParam!=null;
		}
		//status of updating or others.
		else{
			return !dbParam.getId().equals(param.getId());
		}
		
	}

	@Override
	public JPage<Param> getParamsByNameByPage(ServiceContext context,
			JPageable pagination,String name) {
		Page<Param> obj=paramMapper.getParamsByNameByPage(
				toPageRequest(pagination),name);
		return toJPage(obj, pagination);
	}
	
}
