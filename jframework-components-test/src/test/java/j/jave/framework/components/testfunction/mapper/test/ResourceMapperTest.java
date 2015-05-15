package j.jave.framework.components.testfunction.mapper.test;

import j.jave.framework.components.login.mapper.GroupMapper;
import j.jave.framework.components.login.mapper.RoleGroupMapper;
import j.jave.framework.components.login.mapper.RoleMapper;
import j.jave.framework.components.login.mapper.UserExtendMapper;
import j.jave.framework.components.login.mapper.UserGroupMapper;
import j.jave.framework.components.login.mapper.UserMapper;
import j.jave.framework.components.login.mapper.UserRoleMapper;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.components.login.model.Role;
import j.jave.framework.components.login.model.RoleGroup;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.model.UserExtend;
import j.jave.framework.components.login.model.UserGroup;
import j.jave.framework.components.login.model.UserRole;
import j.jave.framework.components.resource.mapper.ResourceExtendMapper;
import j.jave.framework.components.resource.mapper.ResourceGroupMapper;
import j.jave.framework.components.resource.mapper.ResourceMapper;
import j.jave.framework.components.resource.mapper.ResourceRoleMapper;
import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.components.resource.model.ResourceGroup;
import j.jave.framework.components.resource.model.ResourceRole;
import j.jave.framework.components.support.mapperxml.MapperXML;
import j.jave.framework.io.JFile;
import j.jave.framework.model.JBaseModel;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

public class ResourceMapperTest extends AbstractMapperTest {

	
	static Map<Class<?>, Class<? extends JBaseModel>> mapperModels=new HashMap<Class<?>, Class<? extends JBaseModel>>(){
		
		{
			put(UserMapper.class, User.class);
			
			put(RoleMapper.class, Role.class);
			put(UserRoleMapper.class, UserRole.class);
			
			put(GroupMapper.class, Group.class);
			put(UserGroupMapper.class, UserGroup.class);
			put(RoleGroupMapper.class, RoleGroup.class);
			
			put(ResourceMapper.class, Resource.class);
			put(ResourceExtendMapper.class, ResourceExtend.class);
			put(ResourceExtendMapper.class, ResourceExtend.class);
			put(ResourceRoleMapper.class, ResourceRole.class);
			put(ResourceGroupMapper.class, ResourceGroup.class);
			
			put(UserExtendMapper.class, UserExtend.class);
			
		}
		
	};
	
	
	public void testGenetate(){
		for (Iterator<Entry<Class<?>, Class<? extends JBaseModel>>> iterator = mapperModels.entrySet().iterator(); iterator.hasNext();) {
			Entry<Class<?>, Class<? extends JBaseModel>> mapperModel=iterator.next();
			try {
				Class<?> mapper=mapperModel.getKey();
				Class<? extends JBaseModel> model=mapperModel.getValue();
				new MapperXML().generate(mapper, model)
				.write(new JFile(new File(target+"/"+mapper.getSimpleName()+".xml")));
				System.out.println("OK!");
				Assert.assertTrue(true);
			} catch (Exception e) {
				Assert.assertTrue(false);
				throw new RuntimeException(e);
			}
			
		}
	}
	
	
}
