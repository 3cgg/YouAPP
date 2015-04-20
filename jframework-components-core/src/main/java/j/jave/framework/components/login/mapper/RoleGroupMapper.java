/**
 * 
 */
package j.jave.framework.components.login.mapper;

import j.jave.framework.components.login.model.RoleGroup;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="RoleGroupMapper")
@JModelMapper(component="RoleGroupMapper",name=RoleGroup.class)
public interface RoleGroupMapper extends JMapper<RoleGroup> {

	
}
