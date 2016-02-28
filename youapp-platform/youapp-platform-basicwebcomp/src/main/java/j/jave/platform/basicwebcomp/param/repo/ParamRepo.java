/**
 * 
 */
package j.jave.platform.basicwebcomp.param.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.param.model.Param;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author J
 */
public interface ParamRepo<T> extends JIPersist<T,Param> {
	
	public Param getParamByFunctionIdAndCode(String functionId,String code);
	
	public List<Param> getParamByFunctionId(String functionId);
	
	public Page<Param> getParamsByNameByPage(Pageable pageable,String name);
	
}
