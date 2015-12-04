package test.j.jave.framework.core;

import org.springframework.context.support.ClassPathXmlApplicationContext;


public interface Spring {
	static ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-context.xml");	
}
