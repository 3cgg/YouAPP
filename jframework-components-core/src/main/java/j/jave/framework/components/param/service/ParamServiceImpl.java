/**
 * 
 */
package j.jave.framework.components.param.service;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.AbstractBaseService;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.param.mapper.ParamMapper;
import j.jave.framework.components.param.model.Param;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="paramService")
public class ParamServiceImpl extends AbstractBaseService implements ParamService{

	@Autowired
	private ParamMapper paramMapper;
	
	/* (non-Javadoc)
	 * @see j.jave.framework.components.param.service.ParamService#saveParam(j.jave.framework.components.core.context.ServiceContext, j.jave.framework.components.param.model.Param)
	 */
	@Override
	public void saveParam(ServiceContext context, Param param)
			throws ServiceException {
		proxyOnSave(paramMapper, context.getUser(), param);
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.components.param.service.ParamService#updateParam(j.jave.framework.components.core.context.ServiceContext, j.jave.framework.components.param.model.Param)
	 */
	@Override
	public void updateParam(ServiceContext context, Param param)
			throws ServiceException {
		proxyOnUpdate(paramMapper, context.getUser(), param);
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.components.param.service.ParamService#delete(j.jave.framework.components.core.context.ServiceContext, java.lang.String)
	 */
	@Override
	public void delete(ServiceContext context, String id) {
		paramMapper.markDeleted(id);
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.components.param.service.ParamService#getParamById(java.lang.String)
	 */
	@Override
	public Param getParamById(ServiceContext context, String id) {
		return paramMapper.get(id);
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.components.param.service.ParamService#getParamsByPage(j.jave.framework.components.core.context.ServiceContext, j.jave.framework.components.param.model.Param)
	 */
	@Override
	public List<Param> getParamsByPage(ServiceContext context, Param param) {
		return paramMapper.getParamsByPage(param);
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.components.param.service.ParamService#getParamByFunctionIdAndCode(j.jave.framework.components.core.context.ServiceContext, java.lang.String, java.lang.String)
	 */
	@Override
	public Param getParamByFunctionIdAndCode(ServiceContext context,
			String functionId, String code) {
		// TODO Auto-generated method stub
		return paramMapper.getParamByFunctionIdAndCode(functionId, code);
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.components.param.service.ParamService#getParamByFunctionId(j.jave.framework.components.core.context.ServiceContext, java.lang.String)
	 */
	@Override
	public List<Param> getParamByFunctionId(ServiceContext context,
			String functionId) {
		// TODO Auto-generated method stub
		return paramMapper.getParamByFunctionId(functionId);
	}

	
}
