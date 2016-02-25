/**
 * 
 */
package j.jave.platform.basicwebcomp.param.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPagination;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.param.model.Param;

import java.util.List;

/**
 * @author J
 */
public interface ParamService extends Service<Param> {
	
	/**
	 * save param , including some validations. see {@link #exists(ServiceContext, Param)}
	 * @param context 
	 * @param user
	 * @throws JServiceException
	 */
	public void saveParam(ServiceContext context, Param param) throws JServiceException;
	
	
	/**
	 * update param including some validations.  see {@link #exists(ServiceContext, Param)}
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void updateParam(ServiceContext context, Param param) throws JServiceException;
	
	/**
	 * make the record not available
	 * @param context
	 * @param id
	 */
	public void delete(ServiceContext context, String id);
	
	/**
	 * get param according to the primary key.
	 * @param id
	 * @return
	 */
	public Param getParamById(ServiceContext context, String id);
	
	
	public JPage<Param> getParamsByPage(ServiceContext context, JPagination pagination) ;
	
	/**
	 * get param according to the function id and code.
	 * @param context
	 * @param functionId
	 * @param code
	 * @return
	 */
	public Param getParamByFunctionIdAndCode(ServiceContext context, String functionId,String code);
	
	/**
	 * get params according to the function id.
	 * @param context
	 * @param functionId
	 * @return
	 */
	public List<Param> getParamByFunctionId(ServiceContext context, String functionId);

	/**
	 * check if the pram exists in the system, the conditions contain:
	 * <p>1. function id & code is unique.
	 * <p><strong>Note that the primary id of the second parameter is the switch to decide the operation is inserting or others, inserting if null or empty ,otherwise others</strong>  
	 * @param context
	 * @param param
	 * @return
	 */
	boolean exists(ServiceContext context, Param param);
	
	
}
