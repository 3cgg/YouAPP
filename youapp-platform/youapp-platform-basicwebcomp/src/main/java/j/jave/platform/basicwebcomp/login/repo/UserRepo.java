/**
 * 
 */
package j.jave.platform.basicwebcomp.login.repo;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.login.model.User;

import java.util.List;

/**
 * @author J
 */
public interface UserRepo<T> extends JIPersist<T,User> {

	public User getUserByNameAndPassword(String userName,String password);
	
	public User getUserByName(String userName);
	
	public List<User> getUsersByPage(JPageable pagination) ;
	
	public List<User> getUsers();
	
}
