package j.jave.framework.android.test;

import j.jave.framework.android.model.MobileResult;
import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.http.JHttpFactory;
import j.jave.framework.json.JJSON;

public class A {

	public static void main(String[] args) throws Exception {

		JHttpBase<?> post = JHttpFactory.getHttpGet()
				.setUrl("http://www.baissdu.com/")
				.putParam("user.userName", "a")
				.putParam("user.password", "p");

		String page = (String) post.execute();
		MobileResult mobileResult = JJSON.get().parse(page, MobileResult.class);
		System.out.println(mobileResult);

	}
}
