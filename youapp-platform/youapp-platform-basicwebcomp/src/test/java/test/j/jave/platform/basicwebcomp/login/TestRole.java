package test.j.jave.platform.basicwebcomp.login;

import j.jave.kernal.jave.random.JObjectPopulate;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.model.Role;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.login.service.UserService;
import j.jave.platform.basicwebcomp.param.model.Param;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml"})
public class TestRole {

	@Autowired
	private UserService userService;
	
	@Test
	public void testUserService(){
		try{
			ServiceContext context=new ServiceContext();
			User user=new User();
			user.setId("SYSTEM-TEST");
			context.setUser(user);
			
			Role roleData=new Role();
			new JObjectPopulate(roleData).populate();
			roleData.setId(JUniqueUtils.unique().replaceAll("-", ""));
			userService.saveUser(context, roleData);
			
			User dbUser=userService.getById(context, userData.getId());
			dbUser.setPassword("ABC");
			userService.updateUser(context, dbUser);
			
			userService.delete(context, userData.getId());
			dbUser=userService.getById(context, userData.getId());
			System.out.println(dbUser.getDeleted());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
