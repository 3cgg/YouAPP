/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import java.util.List;

import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="ResourceMapper")
@JModelMapper(component="ResourceMapper",name=Resource.class)
public interface ResourceMapper extends JMapper<Resource> {
	
	List<Resource> getResources();
	
	Resource getResourceByURL(@Param(value="url")String url);
	
}
