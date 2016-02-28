/**
 * 
 */
package j.jave.platform.basicwebcomp.param.mapper;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.param.model.Param;
import j.jave.platform.basicwebcomp.param.repo.ParamRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="paramMapper.mapper")
@JModelRepo(component="paramMapper.mapper",name=Param.class)
public interface ParamMapper extends JMapper<Param> ,
ParamRepo<JMapper<Param>>{
	
	public List<Param> getParamsByPage(JPageable pagination) ;
	
	public Param getParamByFunctionIdAndCode(@org.apache.ibatis.annotations.Param(value="functionId")String functionId,
			@org.apache.ibatis.annotations.Param(value="code")String code);
	
	public List<Param> getParamByFunctionId(@org.apache.ibatis.annotations.Param(value="functionId")String functionId);
	
	public Page<Param> getParamsByNameByPage(Pageable pagination,String name);
}
