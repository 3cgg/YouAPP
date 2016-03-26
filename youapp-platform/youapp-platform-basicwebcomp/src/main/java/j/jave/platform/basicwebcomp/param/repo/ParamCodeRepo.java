/**
 * 
 */
package j.jave.platform.basicwebcomp.param.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.param.model.ParamCode;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author J
 */
public interface ParamCodeRepo<T> extends JIPersist<T,ParamCode> {
	
	public ParamCode getParamByTypeIdAndCode(String typeId,String code);
	
	public Page<ParamCode> getParamsByNameByPage(Pageable pageable,String name);
	
	public List<ParamCode> getParamCodeByTypeId(String typeId);
}
