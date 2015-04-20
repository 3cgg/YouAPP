package j.jave.framework.components.param.action;

import j.jave.framework.components.param.model.Param;
import j.jave.framework.components.param.model.ParamSearchCriteria;
import j.jave.framework.components.param.service.ParamService;
import j.jave.framework.components.web.action.AbstractAction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


@Controller(value="param.paramaction")
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ParamAction extends AbstractAction {
	
	private Param  param;
	
	/**
	 * for search criteria 
	 */
	private ParamSearchCriteria paramSearchCriteria;
	
	@Autowired
	private ParamService paramService;
	
	
	public String toRecordParam() throws Exception {
		return "/WEB-INF/jsp/param/record-param.jsp";
	}
	
	public String recordParam() throws Exception {
		paramService.saveParam(getServiceContext(), param);
		setSuccessMessage(CREATE_SUCCESS);
		return "/WEB-INF/jsp/param/record-param.jsp";
	}
	
	
	public String toViewParam() throws Exception {
		
		String id=getParameter("id");
		Param param= paramService.getParamById(getServiceContext(),id);
		if(param!=null){
			setAttribute("youappParam", param);
		}
		return "/WEB-INF/jsp/param/view-param.jsp"; 
	}
	
	public String toViewAllParam() throws Exception {
		Param param=new Param();
		List<Param> params=paramService.getParamsByPage(getServiceContext(), param);
		setAttribute("params", params);
		return "/WEB-INF/jsp/param/view-all-param.jsp";
	}
	
	public String getParamsWithsCondition(){
		List<Param> params=paramService.getParamsByPage(getServiceContext(), paramSearchCriteria);
		setAttribute("params", params);
		return "/WEB-INF/jsp/param/view-all-param.jsp";
	}
	
	public String deleteParam(){
		paramService.delete(getServiceContext(), getParameter("id")); 
		setSuccessMessage(DELETE_SUCCESS);
		return getParamsWithsCondition();
	}
	
	public String toEditParam(){
		Param param=paramService.getParamById(getServiceContext(), getParameter("id"));
		setAttribute("youappParam", param);
		return "/WEB-INF/jsp/param/edit-param.jsp";
	}
	
	public String editParam() throws Exception{
		
		Param dbParam=paramService.getParamById(getServiceContext(), param.getId());
		dbParam.setName(param.getName());
		dbParam.setFunctionId(param.getFunctionId());
		dbParam.setCode(param.getCode());
		dbParam.setDescription(param.getDescription());
		dbParam.setVersion(param.getVersion());
		paramService.updateParam(getServiceContext(), dbParam);
		setAttribute("youappParam", paramService.getParamById(getServiceContext(), dbParam.getId())); 
		setSuccessMessage(EDIT_SUCCESS);
		return "/WEB-INF/jsp/param/edit-param.jsp";
	}
	
}
