package j.jave.framework.temp.test;

import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.http.JHttpFactory;

public class G {

	public static void main(String[] args) throws Exception {
		final String url="http://192.168.0.112:8686/youapp/mobile/service/dispatch/mobile.login.loginaction/login";
		
		JHttpBase<?> post=JHttpFactory.getHttpPost()
		.setUrl(url).putParam("user.userName", "aaa")
		.putParam("user.password", "bbb");
		
         String page = (String) post.execute();
         System.out.println(page);
		
	}
}
