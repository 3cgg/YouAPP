/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="ResourceMapper")
@JModelMapper(component="ResourceMapper",name=Resource.class)
public interface ResourceMapper extends JMapper<Resource> {

	
}
