package j.jave.framework.temp.test;

import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.http.JHttpFactory;

public class H {

	public static void main(String[] args) throws Exception {
		
		
		Class<?> clazz=H.class;
		
		H h=new H();
		
		System.out.println(clazz.getClass());
		
		System.out.println(h.getClass());
		
		JHttpBase<?> base = JHttpFactory.getHttpGet();
		
		base.setUrl("http://www.baidu.com");
		
		Object object=base.execute();
		
		System.out.println(object);
		
	}
}
