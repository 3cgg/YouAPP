/**
 * 
 */
package j.jave.platform.basicwebcomp.login.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.login.model.UserTracker;

import java.util.List;

/**
 * @author J
 */
public interface UserTrackerRepo<P> extends JIPersist<P,UserTracker> {

	
	public List<UserTracker> getUserTrackerByName(String userName);
	
	
}
