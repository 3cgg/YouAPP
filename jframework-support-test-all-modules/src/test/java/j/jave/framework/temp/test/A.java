/**
 * 
 */
package j.jave.framework.temp.test;

import j.jave.framework.commons.support._package.JClassProvidedScanner;
import j.jave.framework.commons.support.detect.JMethodDetect;
import j.jave.framework.commons.support.detect.JMethodInfoProvider;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author J
 *
 */
public class A {

	
	public static void main(String[] args) {
		
		JMethodDetect<B> detect=new JMethodDetect<B>(new JMethodInfoProvider.JMethodInfo<B>() {
			@Override
			public B getInfo(Method method, Class<?> classIncudeMethod) {
				B b=new B();
				b.setMethodName(method.getName());
				return b;
			}
		});
		detect.setPackageScan(new JClassProvidedScanner(B.class));
		detect.detect();
		
		Map<Class<?>, List<B>> res = detect.getClassMethodInfos();
		
		System.out.println(res);
	}
}
