/**
 * 
 */
package j.jave.framework.components.weight.service;

import java.util.List;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.weight.model.Weight;

/**
 * @author Administrator
 *
 */
public interface WeightService {
	
	/**
	 * 
	 * @param context 
	 * @param user
	 * @throws ServiceException
	 */
	public void saveWeight(ServiceContext context, Weight weight) throws ServiceException;
	
	
	/**
	 * 
	 * @param context
	 * @param user
	 * @throws ServiceException
	 */
	public void updateWeight(ServiceContext context, Weight weight) throws ServiceException;
	
	/**
	 * all weight associated to the user. 
	 * @param context
	 * @param userName
	 * @return
	 */
	public List<Weight> getWeightByName(ServiceContext context, String userName);
	
	/**
	 * make the record not available
	 * @param context
	 * @param id
	 */
	public void delete(ServiceContext context, String id);
	
	
	public List<Weight> getWeightsByPage(ServiceContext context,Weight weight) ;
	
	/**
	 * get one .
	 * @param id
	 * @return
	 */
	public Weight getWeightById(String id);
	
	
	
	
	
}
