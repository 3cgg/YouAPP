package test.j.jave.kernal.jave.xml.xmldb;

import java.util.List;

import me.bunny.kernel.jave.proxy.JAtomicResourceProxy;

public class AtomicSessionCheckServiceImpl implements AtomicSessionCheckService{

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
