/**
 * 
 */
package j.jave.framework.components.param.mapper;

import j.jave.framework.commons.model.JPagination;
import j.jave.framework.commons.model.support.JModelMapper;
import j.jave.framework.components.param.model.Param;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="ParamMapper")
@JModelMapper(component="ParamMapper",name=Param.class)
public interface ParamMapper extends JMapper<Param> {
	
	public List<Param> getParamsByPage(JPagination pagination) ;
	
	public Param getParamByFunctionIdAndCode(String functionId,String code);
	
	public List<Param> getParamByFunctionId(String functionId);
	
	
}
