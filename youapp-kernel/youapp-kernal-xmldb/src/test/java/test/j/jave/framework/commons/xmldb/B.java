package test.j.jave.framework.commons.xmldb;

import j.jave.framework.commons.xmldb.JXMLSession;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

public class B extends TestCase {

	
	public void testSession() throws Exception{
		
		JUserXMLPersist userXMLPersist=new JUserXMLPersist();
		
		User user=  userXMLPersist.get("8de576f014e849d79700aea53e454d04",User.class.getName());
		
		//user.setUserName("zhongjin modify");
		
		//xmlSession.update(user);
		
		//xmlSession.commit();
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from User group by userName order by userName ");
		
//		List<Map<String, Object>> users=   xmlSession.select("select userName , count(1) as cont from UserExtend group by userName order by userName ");
		
		List<Map<String, Object>> users=   userXMLPersist.select("select u.userName , ue.userId,count(1) as cont  from User u, UserExtend ue where u.id =ue.userId  group by u.userName ,ue.userId  ");
		
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
