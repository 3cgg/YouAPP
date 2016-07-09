package test.j.jave.kernal.aop.jnterface;

public class TestLoggerServiceImpl implements TestLoggerService {

	

	
	public void log(String msg){
		System.out.println("logging----------------->"+msg);
	}
	
	
}
