/**
 * 
 */
package j.jave.framework.components.weight.service;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.weight.model.Weight;
import j.jave.framework.components.weight.model.WeightSearchCriteria;
import j.jave.framework.model.JPagination;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface WeightService extends Service<Weight> {
	
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
	
	
	public List<Weight> getWeightsByPage(ServiceContext context,JPagination pagination) ;
	
	/**
	 * get one .
	 * @param id
	 * @return
	 */
	public Weight getWeightById(ServiceContext context, String id);
	
	
	
	
	
}
