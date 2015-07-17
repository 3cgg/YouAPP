package test.j.jave.framework.commons.xmldb;

import java.util.List;

public interface UserService {

	public User get(String id);
	
	public List<User> getUser(String userName);
	

	
}
