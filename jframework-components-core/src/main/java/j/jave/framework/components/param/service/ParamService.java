/**
 * 
 */
package j.jave.framework.components.param.service;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.param.model.Param;
import j.jave.framework.model.JPagination;

import java.util.List;

/**
 * @author J
 */
public interface ParamService extends Service<Param> {
	
	/**
	 * 
	 * @param context 
	 * @param user
	 * @throws ServiceException
	 */
	public void saveParam(ServiceContext context, Param param) throws ServiceException;
	
	
	/**
	 * 
	 * @param context
	 * @param user
	 * @throws ServiceException
	 */
	public void updateParam(ServiceContext context, Param param) throws ServiceException;
	
	/**
	 * make the record not available
	 * @param context
	 * @param id
	 */
	public void delete(ServiceContext context, String id);
	
	/**
	 * get one .
	 * @param id
	 * @return
	 */
	public Param getParamById(ServiceContext context, String id);
	
	public List<Param> getParamsByPage(ServiceContext context, JPagination pagination) ;
	
	public Param getParamByFunctionIdAndCode(ServiceContext context, String functionId,String code);
	
	public List<Param> getParamByFunctionId(ServiceContext context, String functionId);
	
	
	
	
	
	
	
	
	
	
	
	
	
}
