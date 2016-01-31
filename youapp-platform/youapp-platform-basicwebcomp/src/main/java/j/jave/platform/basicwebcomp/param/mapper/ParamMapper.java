/**
 * 
 */
package j.jave.platform.basicwebcomp.param.mapper;

import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.mybatis.JMapper;

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
