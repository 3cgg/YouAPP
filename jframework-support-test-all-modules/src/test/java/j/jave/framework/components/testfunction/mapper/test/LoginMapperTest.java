package j.jave.framework.components.testfunction.mapper.test;

import j.jave.framework.commons.io.JFile;
import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.components.login.mapper.GroupMapper;
import j.jave.framework.components.login.mapper.RoleGroupMapper;
import j.jave.framework.components.login.mapper.RoleMapper;
import j.jave.framework.components.login.mapper.UserGroupMapper;
import j.jave.framework.components.login.mapper.UserRoleMapper;
import j.jave.framework.components.login.model.Group;
import j.jave.framework.components.login.model.Role;
import j.jave.framework.components.login.model.RoleGroup;
import j.jave.framework.components.login.model.UserGroup;
import j.jave.framework.components.login.model.UserRole;
import j.jave.framework.components.support.mapperxml.MapperXML;

import java.io.File;

import junit.framework.Assert;

public class LoginMapperTest extends AbstractMapperTest {

	
	public void testGroup(){
		try {
			Class<?> mapper=GroupMapper.class;
			Class<? extends JBaseModel> model=Group.class;
			new MapperXML().generate(mapper, model)
			.write(new JFile(new File(target+"/"+mapper.getSimpleName()+".xml")));
			System.out.println("OK!");
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
			throw new RuntimeException(e);
		}
	}
	
	public void testRole(){
		try {
			Class<?> mapper=RoleMapper.class;
			Class<? extends JBaseModel> model=Role.class;
			new MapperXML().generate(mapper, model)
			.write(new JFile(new File(target+"/"+mapper.getSimpleName()+".xml")));
			System.out.println("OK!");
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
			throw new RuntimeException(e);
		}
	}
	
	
	public void testRoleGroup(){
		try {
			Class<?> mapper=RoleGroupMapper.class;
			Class<? extends JBaseModel> model=RoleGroup.class;
			new MapperXML().generate(mapper, model)
			.write(new JFile(new File(target+"/"+mapper.getSimpleName()+".xml")));
			System.out.println("OK!");
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
			throw new RuntimeException(e);
		}
	}
	
	
	public void testUserRole(){
		try {
			Class<?> mapper=UserRoleMapper.class;
			Class<? extends JBaseModel> model=UserRole.class;
			new MapperXML().generate(mapper, model)
			.write(new JFile(new File(target+"/"+mapper.getSimpleName()+".xml")));
			System.out.println("OK!");
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
			throw new RuntimeException(e);
		}
	}
	
	
	public void testUserGroup(){
		try {
			Class<?> mapper=UserGroupMapper.class;
			Class<? extends JBaseModel> model=UserGroup.class;
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
