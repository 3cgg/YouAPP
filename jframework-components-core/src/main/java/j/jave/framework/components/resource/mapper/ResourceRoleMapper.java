/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import j.jave.framework.components.resource.model.ResourceRole;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="ResourceRoleMapper")
@JModelMapper(component="ResourceRoleMapper",name=ResourceRole.class)
public interface ResourceRoleMapper extends JMapper<ResourceRole> {

	
}
