/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import j.jave.framework.components.resource.model.ResourceGroup;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="ResourceGroupMapper")
@JModelMapper(component="ResourceGroupMapper",name=ResourceGroup.class)
public interface ResourceGroupMapper extends JMapper<ResourceGroup> {

	
}
