package j.jave.framework.hibernate.test.service.impl;

import j.jave.framework.hibernate.JPersist;
import j.jave.framework.hibernate.test.model.Person;
import j.jave.framework.hibernate.test.service.PersonService;

public class PersonServiceImpl implements PersonService {
	
	private JPersist persist;
	
	@Override
	public void save(Person person) {
		persist.save(person);
	}

	@Override
	public Person get(String id) {
		return (Person) persist.get( id,Person.class.getName());
	}

	public void setPersist(JPersist persist) {
		this.persist = persist;
	}
}
