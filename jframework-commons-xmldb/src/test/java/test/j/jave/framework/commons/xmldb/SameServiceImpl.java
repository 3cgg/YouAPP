package test.j.jave.framework.commons.xmldb;

import j.jave.framework.commons.proxy.JAtomicResourceProxy;

import java.util.List;

public class SameServiceImpl implements SameService{

UserService  userServiceImpl=JAtomicResourceProxy.proxy(new UserServiceImpl(), UserService.class);
	
	UserService  userServiceImpl2=JAtomicResourceProxy.proxy(new UserServiceImpl(), UserService.class);
	
	@Override
	public void sameSession() {
		
		User user=  userServiceImpl.get("8de576f014e849d79700aea53e454d04");
		
		//user.setUserName("zhongjin modify");
		
		//xmlSession.update(user);
		
		//xmlSession.commit();
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from User group by userName order by userName ");
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from UserExtend group by userName order by userName ");
		
		List<User> users=   userServiceImpl.getUser("z");
		
		
		
		User user2=  userServiceImpl2.get("8de576f014e849d79700aea53e454d04");
		List<User> users2=   userServiceImpl2.getUser("z");
		
		System.out.println(users.size());
		
		System.out.println("END");
	}
	
}
