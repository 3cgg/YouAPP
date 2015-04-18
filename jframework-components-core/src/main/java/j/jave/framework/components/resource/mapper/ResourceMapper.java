/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="ResourceMapper")
public interface ResourceMapper extends JMapper<Resource> {

	
}
