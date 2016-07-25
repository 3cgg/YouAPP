/**
 * 
 */
package j.jave.web.htmlclient.request;

import j.jave.kernal.jave.model.JModel;

/**
 * @author J
 *
 */
public class ServiceContextVO implements JModel {

	private SessionUserVO user;

	public SessionUserVO getUser() {
		return user;
	}

	public void setUser(SessionUserVO user) {
		this.user = user;
	}
	
	public static ServiceContextVO getSys(){
		ServiceContextVO serviceContext = new ServiceContextVO();
		SessionUserVO sessionUser = new SessionUserVO();
		sessionUser.setId("SYS");
		sessionUser.setNatureName("SYS");
		sessionUser.setUserName("SYS");
		serviceContext.setUser(sessionUser);
		return serviceContext;
	}
	public static ServiceContextVO getSys(String allName){
		ServiceContextVO serviceContext = new ServiceContextVO();
		SessionUserVO sessionUser = new SessionUserVO();
		sessionUser.setId(allName);
		sessionUser.setNatureName(allName);
		sessionUser.setUserName(allName);
		serviceContext.setUser(sessionUser);
		return serviceContext;
	}
	public static ServiceContextVO getSys(String id,String natureName,String userName){
		ServiceContextVO serviceContext = new ServiceContextVO();
		SessionUserVO sessionUser = new SessionUserVO();
		sessionUser.setId(id);
		sessionUser.setNatureName(natureName);
		sessionUser.setUserName(userName);
		serviceContext.setUser(sessionUser);
		return serviceContext;
	}
	
	
	
	
}
