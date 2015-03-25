package test.J.jave.framework.mybatis.unit;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.hub.ServiceFactory;
import j.jave.framework.components.core.hub.ServiceHub;
import j.jave.framework.components.core.hub.ServiceHub.StandardServiceInterfaces;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.service.UserService;
import j.jave.framework.components.memcached.JMemcachedDist;

import java.util.Date;

import junit.framework.TestCase;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UnitTest extends TestCase {

	static ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-context.xml");
	
	private ServiceContext context=new ServiceContext();
	{
		User user=new User();
		user.setId("aaaa-bbbb-cccc-dddd");
		user.setUserName("test-name");
		user.setPassword("test-password");
		context.setUser(user);
	}
	
	public void testSaveUser(){
		UserService userService= (UserService) applicationContext.getBean("userService");
		Object obj=ServiceHub.get().getService(StandardServiceInterfaces.MEMCACHED_DIST_SERVICE);
				
		String userName="N"+new Date().getTime();
		String password="NEVER-AGAIN";
		
		User tempUser=userService.getUserByNameAndPassword(userName, password);
		
		
		User user=new User();
		user.setUserName(userName);
		user.setPassword(password);
		try{
			userService.saveUser(context, user);
			
			Cache cache= (Cache) applicationContext.getBean("ehcacheSource");
			
			Element element=new Element("pro", "j.jave.framework"+new Date().getTime());
			cache.put(element);
			
			JMemcachedDist jMemcachedDist=  (JMemcachedDist) applicationContext.getBean("simpleJMemcachedDist");
			
			String key="abcd";
			
			jMemcachedDist.set(key, 0, "i am from ");
			
			System.out.println(jMemcachedDist.get(key));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		user= userService.getUserByNameAndPassword(userName, password);
		
		System.out.println(user.getId());
	}
	
}
