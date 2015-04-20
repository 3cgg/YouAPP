/**
 * 
 */
package j.jave.framework.components.login.mapper;

import j.jave.framework.components.login.model.UserRole;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="UserRoleMapper")
@JModelMapper(component="UserRoleMapper",name=UserRole.class)
public interface UserRoleMapper extends JMapper<UserRole> {

	
}
