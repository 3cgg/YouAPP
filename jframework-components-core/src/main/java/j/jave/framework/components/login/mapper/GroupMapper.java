/**
 * 
 */
package j.jave.framework.components.login.mapper;

import j.jave.framework.components.login.model.Group;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="GroupMapper")
@JModelMapper(component="GroupMapper",name=Group.class)
public interface GroupMapper extends JMapper<Group> {

	
}
