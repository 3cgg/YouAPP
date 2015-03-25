package test.J.jave.framework.mybatis.unit;

import j.jave.framework.mybatis.test.model.Person;
import j.jave.framework.mybatis.test.service.PersonService;

import java.util.Date;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UnitTest extends TestCase {

	static ClassPathXmlApplicationContext applicationContext=new ClassPathXmlApplicationContext("spring-mybatis.xml");
	
	
	public void testH2(){
	PersonService personService= (PersonService) applicationContext.getBean("personService");
		
		Person person=new Person();
		
		person.setId(String.valueOf(new Date().getTime()));
		
		person.setAge(20);
		person.setDeleted("U1");
		person.setFirstname("U1");
		person.setLastname("U1");
		try{
		personService.save(person);
		}catch(Exception e){
			e.printStackTrace();
		}
		Person db= personService.get(person.getId());
		
		System.out.println(db);
		
		assertNotNull(db);
		
	}
	
}
