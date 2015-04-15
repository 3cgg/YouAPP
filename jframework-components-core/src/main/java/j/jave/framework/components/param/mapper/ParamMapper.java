/**
 * 
 */
package j.jave.framework.components.param.mapper;

import j.jave.framework.components.param.model.Param;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Administrator
 *
 */
@Component(value="ParamMapper")
public interface ParamMapper extends JMapper<Param> {
	
	public List<Param> getParamsByPage(Param param) ;
	
	public Param getParamByFunctionIdAndCode(String functionId,String code);
	
	public List<Param> getParamByFunctionId(String functionId);
	
	
}
