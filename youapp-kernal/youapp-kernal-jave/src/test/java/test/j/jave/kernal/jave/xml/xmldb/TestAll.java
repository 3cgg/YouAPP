package test.j.jave.kernal.jave.xml.xmldb;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import me.bunny.kernel._c.proxy.JAtomicResourceProxy;
import me.bunny.kernel._c.xml.dom4j.util.JXMLUtils;
import me.bunny.kernel._c.xml.xmldb.JXMLSession;
import me.bunny.kernel._c.xml.xmldb.dom4j.JDom4jNodeGetter;

public class TestAll extends TestCase{

	
	public void testSameService() throws Exception{
		
		AtomicSessionCheckService  sameService=JAtomicResourceProxy.proxy(new AtomicSessionCheckServiceImpl(), AtomicSessionCheckService.class);
		sameService.sameSession();
		System.out.println("END");
		
	}
	
	public void testUserService() throws Exception{
		
		UserService  userServiceImpl=JAtomicResourceProxy.proxy(new UserServiceImpl(), UserService.class);
		
		User user=  userServiceImpl.get("8de576f014e849d79700aea53e454d04");
		
		//user.setUserName("zhongjin modify");
		
		//xmlSession.update(user);
		
		//xmlSession.commit();
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from User group by userName order by userName ");
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from UserExtend group by userName order by userName ");
		
		List<User> users=   userServiceImpl.getUser("z");
		
		System.out.println(users.size());
		
		System.out.println("END");
		
	}
	
	
	
	public void testJXMLSession() throws Exception{
		
		JXMLSession xmlSession=new JXMLSession();
		User user=  xmlSession.get("8de576f014e849d79700aea53e454d04",User.class);
		
		//user.setUserName("zhongjin modify");
		
		//xmlSession.update(user);
		
		//xmlSession.commit();
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from User group by userName order by userName ");
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from UserExtend group by userName order by userName ");
		
		List<Map<String, Object>> users=   xmlSession.select("select u.userName , ue.userId,count(1) as cont  from User u, UserExtend ue where u.id =ue.userId  group by u.userName ,ue.userId  ");
		
		System.out.println(users.size());
		
		System.out.println("END");
		
	}
	
	
	public void testJUserXMLPersist() throws Exception{
		
		JUserXMLPersist userXMLPersist=new JUserXMLPersist();
		
		User user=  userXMLPersist.getModel("8de576f014e849d79700aea53e454d04",User.class.getName());
		
		//user.setUserName("zhongjin modify");
		
		//xmlSession.update(user);
		
		//xmlSession.commit();
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from User group by userName order by userName ");
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from UserExtend group by userName order by userName ");
		
		List<Map<String, Object>> users=   userXMLPersist.select("select u.userName , ue.userId,count(1) as cont  from User u, UserExtend ue where u.id =ue.userId  group by u.userName ,ue.userId  ");
		
		System.out.println(users.size());
		
		System.out.println("END");
		
	}
	
	public void testGetter(){
		JDom4jNodeGetter dom4jNodeGetter=new JDom4jNodeGetter(JXMLUtils.loadDocument(
				Thread.currentThread().getContextClassLoader().getResourceAsStream("html.xml")
				));
		
		List<?> element= dom4jNodeGetter.getNodesById("content-wrapper");
		
		List<?> elements1= dom4jNodeGetter.getNodesByName("description");
		
		List<?> elements2= dom4jNodeGetter.getNodesByTagName("a");
		
		List<?> elements3= dom4jNodeGetter.getNodesByClassName("line");
		
		
		
		
		System.out.println("e");
	}
	
	
}
