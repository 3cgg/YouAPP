package test.j.jave.framework.temp.test;

import j.jave.framework.commons.reflect.JReflect;
import j.jave.framework.commons.utils.JStringUtils;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class AAA {

	public static void main(String[] args) throws Exception {
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		URL[] urls=new URL[]{new File("d:/test/test_v1.jar").toURI().toURL()}; 
		URLClassLoader urlClassLoader=new URLClassLoader(urls,classLoader);
		Class<?> clazz=urlClassLoader.loadClass("j.java.test.ServiceImpl");
		System.out.println(clazz+" "+clazz.hashCode());
		JReflect.invoke(clazz.newInstance(), "show", new Object[]{});
		urlClassLoader.close();
		
		urls=new URL[]{new File("d:/test/test_v2.jar").toURI().toURL()}; 
		urlClassLoader=new URLClassLoader(urls,classLoader);
		Class<?> clazz2=urlClassLoader.loadClass("j.java.test.ServiceImpl");
		System.out.println(clazz2+" "+clazz2.hashCode());
		JReflect.invoke(clazz2.newInstance(), "show", new Object[]{});
		urlClassLoader.close();
		
		System.out.println(clazz==clazz2);
		
		
		Class<?> stringClazz=JStringUtils.class;
		
		Class<?> stringClass=classLoader.loadClass("j.jave.framework.utils.JStringUtils");
		
		Class<?> sss=urlClassLoader.loadClass("j.jave.framework.utils.JStringUtils");
		
		System.out.println(stringClass==stringClazz);
		
		
		urls=new URL[]{new File("d:/test/test_v2.jar").toURI().toURL()}; 
		urlClassLoader=new URLClassLoader(urls,classLoader);
		Class<?> clazz3=urlClassLoader.loadClass("j.java.test.ServiceImpl");
		System.out.println(clazz3+" "+clazz3.hashCode());
		System.out.println(clazz3==clazz2);
		JReflect.invoke(clazz3.newInstance(), "show", new Object[]{});
		urlClassLoader.close();
		
		urls=new URL[]{new File("d:/test/test_v3.jar").toURI().toURL()}; 
		urlClassLoader=new URLClassLoader(urls,classLoader);
		Class<?> clazz4=urlClassLoader.loadClass("j.java.test.ServiceImpl");
		System.out.println(clazz4+" "+clazz4.hashCode());
		JReflect.invoke(clazz4.newInstance(), "show", new Object[]{});
		urlClassLoader.close();
		
		Class<?> class5= classLoader.loadClass("j.java.test.ServiceImpl");
		
		System.out.println(class5==clazz4);
		System.out.println(class5==clazz3);
		
		urls=new URL[]{new File("d:/test/test_v3.jar").toURI().toURL()}; 
		urlClassLoader=new URLClassLoader(urls,classLoader);
		Class<?> clazz5=urlClassLoader.loadClass("j.java.test.ServiceImpl");
		System.out.println(clazz5+" "+clazz5.hashCode());
		JReflect.invoke(clazz5.newInstance(), "show", new Object[]{});
		urlClassLoader.close();
	}
	
	
}
