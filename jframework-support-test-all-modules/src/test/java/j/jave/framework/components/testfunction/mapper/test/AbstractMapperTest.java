package j.jave.framework.components.testfunction.mapper.test;

import j.jave.framework.commons.io.JFile;
import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.components.support.mapperxml.MapperXML;

import java.io.File;

import junit.framework.Assert;
import junit.framework.TestCase;

public abstract class AbstractMapperTest extends TestCase {

	protected String path=this.getClass().getClassLoader().getResource("").getPath();
	protected String split="target/test-classes/";
	protected String target=path.replace(split, "src\\test\\java\\j\\jave\\framework\\components\\testfunction\\mapper\\test\\");
	
	{
		System.out.println("target-------->"+target);
	}
	
	protected <T extends JBaseModel> void write(Class<?> namespace,Class<T> model){
		try {
			new MapperXML().generate(namespace, model)
			.write(new JFile(new File(target+"/"+namespace.getSimpleName()+".xml")));
			System.out.println("OK!");
			Assert.assertTrue(true);
		} catch (Exception e) {
			Assert.assertTrue(false);
			throw new RuntimeException(e);
		}
	}
	
}
