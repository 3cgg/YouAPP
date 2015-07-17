package test.j.jave.framework.commons.xmldb;

import java.util.List;

import junit.framework.TestCase;

public class C extends TestCase {

	
	public void testSession() throws Exception{
		
		UserServiceImpl  userServiceImpl=new UserServiceImpl();
		
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
	
	public static void main(String[] args) throws Exception {
//		File file=new File(new URI("file:/D:/XML-DATA/USER.xml"));
//		System.out.println(file.exists());
//		
//		FileOutputStream fileOutputStream=new FileOutputStream(file);
//		System.out.println(fileOutputStream);
	}
}
