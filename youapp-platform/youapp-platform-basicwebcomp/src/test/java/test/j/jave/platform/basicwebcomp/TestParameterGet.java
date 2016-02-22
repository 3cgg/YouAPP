package test.j.jave.platform.basicwebcomp;

import j.jave.platform.basicwebcomp.web.util.ParameterNameGet;
import junit.framework.TestCase;

public class TestParameterGet extends TestCase{

	public void show(String name,String age){
		
	}
	private Integer getAge(){
		
		return 0;
	}
	
	public void testGetParameter(){
		try{
			String[] params=ParameterNameGet.getMethodParameterNamesByAsm4(TestParameterGet.class,
					TestParameterGet.class.getMethod("show", String.class,String.class));
			
			System.out.println(params);
		}catch(Exception e){e.printStackTrace();}
		
	}
	
}
