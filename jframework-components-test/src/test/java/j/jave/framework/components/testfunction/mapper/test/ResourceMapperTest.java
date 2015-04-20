package j.jave.framework.components.testfunction.mapper.test;

import j.jave.framework.components.resource.mapper.ResourceExtendMapper;
import j.jave.framework.components.resource.mapper.ResourceGroupMapper;
import j.jave.framework.components.resource.mapper.ResourceRoleMapper;
import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.components.resource.model.ResourceGroup;
import j.jave.framework.components.resource.model.ResourceRole;
import j.jave.framework.components.support.mapperxml.MapperXML;
import j.jave.framework.io.JFile;
import j.jave.framework.model.JBaseModel;

import java.io.File;

import junit.framework.Assert;

public class ResourceMapperTest extends AbstractMapperTest {

	
	public void testResourceGroup(){
		try {
			Class<?> mapper=ResourceGroupMapper.class;
			Class<? extends JBaseModel> model=ResourceGroup.class;
			new MapperXML().generate(mapper, model)
			.write(new JFile(new File(target+"/"+mapper.getSimpleName()+".xml")));
			System.out.println("OK!");
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
			throw new RuntimeException(e);
		}
	}
	
	public void testResourceExtend(){
		try {
			Class<?> mapper=ResourceExtendMapper.class;
			Class<? extends JBaseModel> model=ResourceExtend.class;
			new MapperXML().generate(mapper, model)
			.write(new JFile(new File(target+"/"+mapper.getSimpleName()+".xml")));
			System.out.println("OK!");
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
			throw new RuntimeException(e);
		}
	}
	
	
	public void testResourceRole(){
		try {
			Class<?> mapper=ResourceRoleMapper.class;
			Class<? extends JBaseModel> model=ResourceRole.class;
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
