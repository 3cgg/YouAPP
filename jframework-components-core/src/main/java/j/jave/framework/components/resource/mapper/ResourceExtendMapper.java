/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="ResourceExtendMapper")
@JModelMapper(component="ResourceExtendMapper",name=ResourceExtend.class)
public interface ResourceExtendMapper extends JMapper<ResourceExtend> {

	
}
