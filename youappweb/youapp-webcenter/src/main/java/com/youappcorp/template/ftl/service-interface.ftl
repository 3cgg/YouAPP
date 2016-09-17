package ${classPackage};

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;

<#list models as model>
import ${model.modelModel.className};
import ${model.modelRecordModel.className};
import ${model.criteriaModel.className};
</#list>



public interface ${simpleClassName} {

<#list models as model>
	/**
	 * save
	 */
	void ${model.serviceModel.saveMethodName} (${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName});
	
	/**
	 * update
	 */
	void ${model.serviceModel.updateMethodName} (${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName});
	
	/**
	 * delete
	 */
	void ${model.serviceModel.deleteMethodName} (${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName});
	
	/**
	 * delete
	 */
	void ${model.serviceModel.deleteByIdMethodName} (String id);
	
	/**
	 * get
	 */
	${model.modelRecordModel.simpleClassName} ${model.serviceModel.getMethodName} (String id);
	
	/**
	 * page...
	 */
	JPage<${model.modelRecordModel.simpleClassName}> ${model.serviceModel.pageMethodName}(${model.criteriaModel.simpleClassName} ${model.criteriaModel.variableName}, JSimplePageable simplePageable);

</#list>
	
}
