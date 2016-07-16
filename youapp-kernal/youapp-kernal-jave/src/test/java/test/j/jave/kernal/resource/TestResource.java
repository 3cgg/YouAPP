package test.j.jave.kernal.resource;

import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.JResourceContainer;
import j.jave.kernal.container.JResourceContainerConfig;
import j.jave.kernal.container._resource.JResourceAccessService;
import j.jave.kernal.container._resource.JResourceURIParserService;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

import java.io.File;
import java.net.URI;
import java.net.URL;

import org.junit.Test;

import test.j.jave.kernal.eventdriven.TestEventSupport;

public class TestResource extends TestEventSupport {

	private JResourceURIParserService parser=JServiceHubDelegate.get()
			.getService(this, JResourceURIParserService.class);
	
	private JResourceAccessService resourceAccessService=JServiceHubDelegate.get()
			.getService(this, JResourceAccessService.class); 
	
	private JContainerDelegate containerDelegate=JContainerDelegate.get();
	
	@Test
	public void testURI(){
		
		String file="d:/data.sql";
		
		
		try {
			URI uri=new URI(new File(file).toURI().toString());
			
			URI reURI=parser.parse(uri, "a");
			
			Object obj=resourceAccessService.execute(reURI,null);
			
//			resourceAccessService.execute(parser.parse(new File("d:/mid.txt").toURI(),
//					"d",Type.PUT), obj);
			
			obj=resourceAccessService
			.execute(parser.parse(new URL("http://www.baidu.com").toURI(), "S"), null);			
			
			System.out.println("d");
			
			
			JResourceContainer resourceContainer= containerDelegate.getContainer(JResourceContainerConfig.DEFAULT_UNIQUE);
			URI exeUri=resourceContainer.resourceURIParser()
			.parse(new URL("http://www.baidu.com").toURI());
			obj=containerDelegate.execute(exeUri, null, resourceContainer.unique());
			
			System.out.println(obj);
			
			
			obj=resourceAccessService.execute(uri, "");
			System.out.println(obj);
			
			
			
		} catch (Exception e) {
			
			
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	
}
