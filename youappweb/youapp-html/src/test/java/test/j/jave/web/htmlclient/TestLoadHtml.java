package test.j.jave.web.htmlclient;

import j.jave.web.htmlclient.ModuleInstallEvent;
import j.jave.web.htmlclient.ModuleMeta;
import me.bunny.kernel.jave.json.JJSON;

import java.io.File;

import org.junit.Test;

public class TestLoadHtml extends TestEventSupport {
	
	@Test
	public void testLoadHtml(){
		try{
			ModuleMeta moduleMeta=new ModuleMeta();
			moduleMeta.setJarUrl(new File("d:/youapp-business-bill-1.0.0.jar").toURI().toString());
			String moduleMetaStr=JJSON.get().formatObject(moduleMeta);
			ModuleInstallEvent moduleInstallEvent=new ModuleInstallEvent(this, moduleMetaStr);
			
			serviceHubDelegate.addImmediateEvent(moduleInstallEvent);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
}
