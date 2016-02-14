package test.j.jave.kernal.http;

import j.jave.kernal.http.extension.JHttpFactoryProvider;
import junit.framework.TestCase;

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
