/**
 * 
 */
package j.jave.platform.basicwebcomp.param.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.param.model.CodeTableCacheModel;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.model.ParamType;

import java.util.List;

/**
 * @author J
 */
public interface ParamService extends Service<ParamCode> {
	
	/**
	 * save param , including some validations. see {@link #exists(ServiceContext, ParamCode)}
	 * @param context 
	 * @param paramType
	 * @param paramCode
	 * @throws JServiceException
	 */
	public void saveParam(ServiceContext context, ParamType paramType, ParamCode paramCode) throws JServiceException;
	
	
	/**
	 * update param including some validations.  see {@link #exists(ServiceContext, ParamCode)}
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void updateParam(ServiceContext context, ParamType paramType, ParamCode paramCode) throws JServiceException;

	/**
	 * 
	 * @param context
	 * @return
	 */
	public List<CodeTableCacheModel> getCodeTableCacheModels(ServiceContext context); 
	
}
