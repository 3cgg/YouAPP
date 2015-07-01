package j.jave.framework.components.testfunction.mapper.test;

import j.jave.framework.commons.io.JFile;
import j.jave.framework.commons.model.JBaseModel;
import j.jave.framework.components.support.mapperxml.MapperXML;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.Assert;

public class RuntimeLoadMapperTest extends AbstractMapperTest {

	
	static Map<Class<?>, Class<? extends JBaseModel>> mapperModels=new HashMap<Class<?>, Class<? extends JBaseModel>>(){
		
		{
//			put(RuntimeLoadMapper.class, RuntimeLoad.class);
			
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
