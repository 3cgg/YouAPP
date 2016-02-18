package test.j.jave.kernal.jave.xml.xmldb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

	private JUserXMLPersist userXMLPersist=new JUserXMLPersist();
	
	@Override
	public User get(String id) {
		return userXMLPersist.getModel(id, User.class.getName());
	}
	
	@Override
	public List<User> getUser(String userName) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userName", userName);
		return userXMLPersist.select("from User where userName=:userName", params,User.class);
	}
	
	

}
