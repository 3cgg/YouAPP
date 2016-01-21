package test.j.jave.framework.commons.xmldb;

import j.jave.framework.commons.proxy.JAtomicResourceProxy;
import junit.framework.TestCase;

public class E extends TestCase {

	
	public void testSession() throws Exception{
		
		SameService  sameService=JAtomicResourceProxy.proxy(new SameServiceImpl(), SameService.class);
		sameService.sameSession();
		System.out.println("END");
		
	}
	
	public static void main(String[] args) throws Exception {
//		File file=new File(new URI("file:/D:/XML-DATA/USER.xml"));
//		System.out.println(file.exists());
//		
//		FileOutputStream fileOutputStream=new FileOutputStream(file);
//		System.out.println(fileOutputStream);
	}
}
