package j.jave.framework.hibernate.test.service;

import j.jave.framework.hibernate.test.model.Person;

public interface PersonService {

	public void save(Person person);
	
	public Person get(String id);
	
}
