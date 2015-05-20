/**
 * 
 */
package j.jave.framework.components.param.service;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceSupport;
import j.jave.framework.components.param.mapper.ParamMapper;
import j.jave.framework.components.param.model.Param;
import j.jave.framework.model.JPagination;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.utils.JStringUtils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * parameter basic service.
 * @author J
 */
@Service(value="paramService.transation")
public class ParamServiceImpl extends ServiceSupport<Param> implements ParamService{

	@Autowired
	private ParamMapper paramMapper;
	
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
	public List<Param> getParamsByPage(ServiceContext context, JPagination pagination) {
		return paramMapper.getParamsByPage(pagination);
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
	protected JMapper<Param> getMapper() {
		return this.paramMapper;
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
		
		// status of inserting 
		if(JStringUtils.isNullOrEmpty(param.getId())){
			return dbParam!=null;
		}
		//status of updating or others.
		else{
			return !dbParam.getId().equals(param.getId());
		}
		
	}
	
}
