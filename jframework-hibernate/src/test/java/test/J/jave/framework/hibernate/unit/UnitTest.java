package test.J.jave.framework.hibernate.unit;

import j.jave.framework.hibernate.test.model.Person;
import j.jave.framework.hibernate.test.service.PersonService;

import java.util.Date;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UnitTest extends TestCase {

	static ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-hibernate.xml");
	
	public void testH2(){

		
		
		PersonService personService= (PersonService) applicationContext.getBean("personService");
		
		Person person=new Person();
		
		person.setId(String.valueOf(new Date().getTime()));
		
		person.setAge(20);
		person.setDeleted("NNN");
		person.setFirstname("123");
		person.setLastname("SSJ");
		
		personService.save(person);
		
		Person db= personService.get(person.getId());
		
		System.out.println(db);
	}
	
	
	
}
