package test.j.jave.kernal.http;

import junit.framework.TestCase;
import me.bunny.kernel.http.JHttpFactoryProvider;

public class TestHttp extends TestCase {

	public void testGet(){
		
		try {
			Object object=JHttpFactoryProvider.getHttpFactory().getHttpGet().execute("https://www.baidu.com/");
			System.out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
