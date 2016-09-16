package ${serviceModel.servicePackage};

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;

import com.youappcorp.project.sysparam.model.SysParam;
import com.youappcorp.project.sysparam.vo.SysParamCriteriaInVO;

public interface ${serviceModel.serviceSimpleClassName} {

	/**
	 * save
	 */
	void ${serviceModel.saveMethodName} (${modelModel.modelSimpleClassName} ${modelModel.variableName});
	
	/**
	 * update
	 */
	void ${serviceModel.updateMethodName} (${modelModel.modelSimpleClassName} ${modelModel.variableName});
	
	/**
	 * delete
	 */
	void ${serviceModel.deleteMethodName} (${modelModel.modelSimpleClassName} ${modelModel.variableName});
	
	/**
	 * delete
	 */
	void ${serviceModel.deleteByIdMethodName} (String id);
	
	/**
	 * get
	 */
	SysParam ${serviceModel.getMethodName} (String id);
	
	/**
	 * page...
	 */
	JPage<SysParam> ${serviceModel.pageMethodName}(${criteriaModel.criteriaSimpleClassName} ${criteriaModel.variableName}, JSimplePageable simplePageable);
	
}
