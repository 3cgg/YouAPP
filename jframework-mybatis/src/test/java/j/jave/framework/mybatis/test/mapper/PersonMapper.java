/**
 * 
 */
package j.jave.framework.mybatis.test.mapper;

import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;
import j.jave.framework.mybatis.test.model.Person;

import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="personMapper")
@JModelMapper(component="personMapper",name=Person.class)
public interface PersonMapper extends JMapper<Person> {

}
