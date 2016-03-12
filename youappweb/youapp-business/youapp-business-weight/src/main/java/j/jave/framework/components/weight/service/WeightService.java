/**
 * 
 */
package j.jave.framework.components.weight.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.model.JPagination;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.weight.model.Weight;

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
	 * @throws JServiceException
	 */
	public void saveWeight(ServiceContext context, Weight weight) throws JServiceException;
	
	
	/**
	 * 
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void updateWeight(ServiceContext context, Weight weight) throws JServiceException;
	
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
