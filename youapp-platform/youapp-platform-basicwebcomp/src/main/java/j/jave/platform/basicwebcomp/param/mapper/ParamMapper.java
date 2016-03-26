/**
 * 
 */
package j.jave.platform.basicwebcomp.param.mapper;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.param.model.ParamCode;
import j.jave.platform.basicwebcomp.param.repo.ParamCodeRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="paramMapper.mapper")
@JModelRepo(component="paramMapper.mapper",name=ParamCode.class)
public interface ParamMapper extends JMapper<ParamCode> ,
ParamCodeRepo<JMapper<ParamCode>>{
	
	public List<ParamCode> getParamsByPage(JPageable pagination) ;
	
	public ParamCode getParamByFunctionIdAndCode(@org.apache.ibatis.annotations.Param(value="functionId")String functionId,
			@org.apache.ibatis.annotations.Param(value="code")String code);
	
	public List<ParamCode> getParamByFunctionId(@org.apache.ibatis.annotations.Param(value="functionId")String functionId);
	
	public Page<ParamCode> getParamsByNameByPage(Pageable pagination,String name);
}
