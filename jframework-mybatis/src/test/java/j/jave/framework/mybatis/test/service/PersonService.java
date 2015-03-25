package j.jave.framework.mybatis.test.service;

import j.jave.framework.mybatis.test.model.Person;

public interface PersonService {

	public void save(Person person);
	
	public Person get(String id);
	
}
