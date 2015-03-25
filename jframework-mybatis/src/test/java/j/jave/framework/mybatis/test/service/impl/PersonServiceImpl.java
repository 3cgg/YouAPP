package j.jave.framework.mybatis.test.service.impl;

import j.jave.framework.mybatis.test.mapper.PersonMapper;
import j.jave.framework.mybatis.test.model.Person;
import j.jave.framework.mybatis.test.service.PersonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service(value="personService")
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonMapper personMapper;
	
	@Override
	public void save(Person person) {
		personMapper.save(person);
//		throw new RuntimeException("roolback Manually");
	}

	@Override
	public Person get(String id) {
		return (Person) personMapper.get(id);
	}

	/**
	 * @param personMapper the personMapper to set
	 */
	public void setPersonMapper(PersonMapper personMapper) {
		this.personMapper = personMapper;
	}
}
