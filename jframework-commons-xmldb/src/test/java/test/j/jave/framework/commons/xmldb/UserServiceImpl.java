package test.j.jave.framework.commons.xmldb;

import j.jave.framework.commons.proxy.JAtomicResourceProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

	private JUserXMLPersist userXMLPersist=new JUserXMLPersist();
	
	@Override
	public User get(String id) {
		return userXMLPersist.get(id, User.class.getName());
	}
	
	@Override
	public List<User> getUser(String userName) {
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userName", userName);
		return userXMLPersist.select("from User where userName=:userName", params,User.class);
	}
	
	

}
