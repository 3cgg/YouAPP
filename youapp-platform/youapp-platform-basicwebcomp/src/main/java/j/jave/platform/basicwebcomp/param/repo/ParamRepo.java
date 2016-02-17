/**
 * 
 */
package j.jave.platform.basicwebcomp.param.repo;

import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.param.model.Param;

import java.util.List;

/**
 * @author J
 */
public interface ParamRepo<T> extends JIPersist<T,Param> {
	
	public List<Param> getParamsByPage(JPagination pagination) ;
	
	public Param getParamByFunctionIdAndCode(String functionId,String code);
	
	public List<Param> getParamByFunctionId(String functionId);
	
	
}
