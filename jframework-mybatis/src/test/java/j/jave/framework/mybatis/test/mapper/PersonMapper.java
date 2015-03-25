/**
 * 
 */
package j.jave.framework.mybatis.test.mapper;

import j.jave.framework.mybatis.JMapper;
import j.jave.framework.mybatis.test.model.Person;

import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="personMapper")
public interface PersonMapper extends JMapper<Person> {

}
