/**
 * 
 */
package j.jave.platform.basicwebcomp.login.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.login.model.UserExtend;

/**
 * @author J
 */
public interface UserExtendRepo<T> extends JIPersist<T,UserExtend > {
	
	public UserExtend getUserExtendByUserId(String userId);

	public UserExtend getUserExtendByNatureName(String natureName);
	
}
