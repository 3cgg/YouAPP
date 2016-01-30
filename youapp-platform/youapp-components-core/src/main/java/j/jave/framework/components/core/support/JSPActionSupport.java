/**
 * 
 */
package j.jave.framework.components.core.support;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.core.service.ServiceContextSupport;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.web.jsp.JSPAction;

/**
 * JSP Request Support. 
 * @author J
 */
public class JSPActionSupport extends JSPAction  implements ServiceContextSupport{

	public ServiceContext getServiceContext(){
		ServiceContext serviceContext=new ServiceContext();
		serviceContext.setUser((User) getSessionUser());
		return serviceContext;
	}
	
	
}
