package test.com.youappcorp.project.billmanager.alertmanager;

import org.junit.Test;

import test.com.youappcorp.project.billmanager.TestEventSupport;

import com.youappcorp.template.ftl.Config;
import com.youappcorp.template.ftl.TemplateRunner;

public class TemplateProcessor extends TestEventSupport {

	
	@Test
	public void processing(){
		try{
			Config config=new Config();
			config.setModelPath("D:\\java_\\git-project\\YouAPP\\youappweb\\youapp-business\\youapp-business-home\\src\\main\\java\\com\\youappcorp\\project\\alertmanager\\model");
			config.setUiRelativePath("D:\\java_\\git-project\\YouAPP\\youappweb\\youapp-html\\src\\main\\webapp");
			config.setModuleName("AlertManager");
			config.setJavaCode(false);
			TemplateRunner.start(config);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
}
