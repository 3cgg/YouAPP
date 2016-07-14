package test.j.jave.kernal.resource;

import j.jave.kernal.container.scheme.JResourceAccessService;
import j.jave.kernal.container.scheme.JSchemeURIParserService;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

import java.io.File;
import java.net.URI;

import org.junit.Test;

import test.j.jave.kernal.eventdriven.TestEventSupport;

public class TestResource extends TestEventSupport {

	private JSchemeURIParserService parser=JServiceHubDelegate.get()
			.getService(this, JSchemeURIParserService.class);
	
	private JResourceAccessService resourceAccessService=JServiceHubDelegate.get()
			.getService(this, JResourceAccessService.class); 
	
	@Test
	public void testURI(){
		
		String file="d:/data.sql";
		
		
		try {
			URI uri=new URI(new File(file).toURI().toString());
			
			URI reURI=parser.parse(uri, "a");
			
			Object obj=resourceAccessService.execute(reURI);
			
			System.out.println("d");
			
		} catch (Exception e) {
			
			
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	
}
