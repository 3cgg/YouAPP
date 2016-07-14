package test.j.jave.kernal.resource;

import j.jave.kernal.container.JExecutableURIUtil.Type;
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
	
	@Test
	public void testURI(){
		
		String file="d:/BugReport.txt";
		
		
		try {
			URI uri=new URI(new File(file).toURI().toString());
			
			URI reURI=parser.parse(uri, "a");
			
			Object obj=resourceAccessService.execute(reURI,null);
			
//			resourceAccessService.execute(parser.parse(new File("d:/mid.txt").toURI(),
//					"d",Type.PUT), obj);
			
			obj=resourceAccessService
			.execute(parser.parse(new URL("http://www.baidu.com").toURI(), "S"), null);			
			
			System.out.println("d");
			
		} catch (Exception e) {
			
			
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	
}
