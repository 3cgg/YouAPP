package j.jave.framework.cxf;

import java.util.Date;

import javax.jws.WebService;

@WebService(endpointInterface = "j.jave.framework.cxf.HelloWorld")
public class HelloWorldImpl implements HelloWorld {

	public String sayHi(String text) {
		System.out.println("sayHi called");
		return "Hello " + text;
	}

	
	@Override
	public void run(String condition) {
		System.out.println(new Date());
	}
	
	
}
