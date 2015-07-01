/**
 * 
 */
package j.jave.framework.components.runtimeload.mapper;

import j.jave.framework.commons.model.support.JModelMapper;
import j.jave.framework.components.runtimeload.model.RuntimeLoad;
import j.jave.framework.mybatis.JMapper;

import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="RuntimeLoadMapper")
@JModelMapper(component="RuntimeLoadMapper",name=RuntimeLoad.class)
public interface RuntimeLoadMapper extends JMapper<RuntimeLoad> {
	
}
