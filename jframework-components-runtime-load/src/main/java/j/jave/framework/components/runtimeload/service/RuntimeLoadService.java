/**
 * 
 */
package j.jave.framework.components.runtimeload.service;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.runtimeload.model.RuntimeLoad;

/**
 * @author J
 */
public interface RuntimeLoadService extends Service<RuntimeLoad> {
	
	public void saveRuntimeLoad(ServiceContext context, RuntimeLoad bill) throws JServiceException;
	
	public void updateRuntimeLoad(ServiceContext context, RuntimeLoad bill) throws JServiceException;
	
	public void delete(ServiceContext context, String id);
	
	public RuntimeLoad getRuntimeLoadById(ServiceContext context, String id);
	
	public String running();
	
}
