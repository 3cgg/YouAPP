package test.com.youappcorp.template.ftl;

import org.junit.Test;

import com.youappcorp.template.ftl.Config;
import com.youappcorp.template.ftl.Config.FieldConfig;
import com.youappcorp.template.ftl.TemplateRunner;

public class TestRepo extends TestEventSupport {

	
	@Test
	public void testRepo(){
		try{
			Config config=new Config();
			config.setModelPath("D:\\java_\\git-project\\YouAPP\\youappweb\\youapp-webcenter\\src\\test\\java\\test\\com\\youappcorp\\template\\ftl\\testmanager\\model");
			config.setUiRelativePath("D:\\java_\\git-project\\YouAPP\\youappweb\\youapp-webcenter\\src\\test\\java\\test\\com\\youappcorp\\template\\ftl\\testmanager\\file");
			
			config.setModuleName("TestManager");
			
			config.addUIField(new FieldConfig("code", "编码"));
			config.addUIField(new FieldConfig("name", "名称"));
			config.addUIField(new FieldConfig("description", "描述"));
			
			
			TemplateRunner.start(config);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
