package test.j.jave.platform.basicwebcomp.login;

import j.jave.kernal.jave.random.JObjectPopulate;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.login.model.Role;
import j.jave.platform.basicwebcomp.login.model.User;
import j.jave.platform.basicwebcomp.login.service.RoleService;
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
	private RoleService roleService;
	
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
			roleService.saveRole(context, roleData);
			
			Role dbRole=roleService.getById(context, roleData.getId());
			dbRole.setDescription("SYS-DESC");
			roleService.updateRole(context, dbRole);
			
			roleService.delete(context, roleData.getId());
			dbRole=roleService.getById(context, roleData.getId());
			System.out.println(dbRole.getDeleted());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
