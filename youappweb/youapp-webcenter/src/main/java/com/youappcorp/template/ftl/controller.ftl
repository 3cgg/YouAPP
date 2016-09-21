package ${classPackage};

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

<#list models as model>
import ${model.modelModel.className};
import ${model.modelRecordModel.className};
import ${model.modelRecordVOModel.className};
import ${model.criteriaModel.className};
import ${model.serviceModel.className};
</#list>


@Controller
@RequestMapping("${controllerBaseMapping}")
public class ${simpleClassName} extends SimpleControllerSupport {

<#list models as model>
<#if model_index = 0>
	@Autowired
	private ${model.serviceModel.simpleClassName} ${model.serviceModel.variableName};
	
</#if>
</#list>

<#list models as model>
	/**
	 * save
	 */
	@ResponseBody
	@RequestMapping("/${model.controllerModel.saveMethodName}")
	public ResponseModel ${model.controllerModel.saveMethodName} (${model.modelRecordVOModel.simpleClassName} ${model.modelRecordVOModel.variableName}) throws Exception {
		// do something validation on the ${model.modelRecordVOModel.variableName}.
		${model.serviceModel.variableName}.${model.serviceModel.saveMethodName}( ${model.modelRecordVOModel.variableName});
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * update
	 */
	@ResponseBody
	@RequestMapping("/${model.controllerModel.updateMethodName}")
	public ResponseModel ${model.controllerModel.updateMethodName} (${model.modelRecordVOModel.simpleClassName} ${model.modelRecordVOModel.variableName}) throws Exception {
		// do something validation on the ${model.modelRecordVOModel.variableName}.
		${model.serviceModel.variableName}.${model.serviceModel.updateMethodName}( ${model.modelRecordVOModel.variableName});
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/${model.controllerModel.deleteMethodName}")
	public ResponseModel ${model.controllerModel.deleteMethodName} (${model.modelRecordVOModel.simpleClassName} ${model.modelRecordVOModel.variableName}) throws Exception {
		// do something validation on the ${model.modelRecordVOModel.variableName}.
		${model.serviceModel.variableName}.${model.serviceModel.deleteMethodName}( ${model.modelRecordVOModel.variableName});
		return ResponseModel.newSuccess(true);
	}
	
	/**
	 * delete
	 */
	@ResponseBody
	@RequestMapping("/${model.controllerModel.deleteByIdMethodName}")
	public ResponseModel ${model.controllerModel.deleteByIdMethodName} (String id) throws Exception {
		// do something validation on the ${model.modelRecordVOModel.variableName}.
		${model.serviceModel.variableName}.${model.serviceModel.deleteByIdMethodName}( id);
		return ResponseModel.newSuccess(true);
	}
	
	
	private ${model.modelRecordVOModel.simpleClassName} to${model.modelRecordVOModel.simpleClassName}(${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName}) {
		return JObjectUtils.simpleCopy(${model.modelRecordModel.variableName}, ${model.modelRecordVOModel.simpleClassName}.class);
	}
	
	private List<${model.modelRecordVOModel.simpleClassName}> to${model.modelRecordVOModel.simpleClassName}s(List<${model.modelRecordModel.simpleClassName}> ${model.modelRecordModel.variableName}s) {
		List<${model.modelRecordVOModel.simpleClassName}> ${model.modelRecordVOModel.variableName}s=new ArrayList<${model.modelRecordVOModel.simpleClassName}>();
		for(${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName}:${model.modelRecordModel.variableName}s){
			${model.modelRecordVOModel.variableName}s.add(to${model.modelRecordVOModel.simpleClassName}(${model.modelRecordModel.variableName}));
		}
		return ${model.modelRecordVOModel.variableName}s;
	}
	
	private void to${model.modelRecordVOModel.simpleClassName}Page(JPage<${model.modelRecordModel.simpleClassName}> ${model.modelRecordModel.variableName}sPage) {
		${model.modelRecordModel.variableName}sPage.setContent(to${model.modelRecordVOModel.simpleClassName}s(${model.modelRecordModel.variableName}sPage.getContent()));
	}
	
	
	/**
	 * get
	 */
	@ResponseBody
	@RequestMapping("/${model.controllerModel.getMethodName}")
	public ResponseModel ${model.controllerModel.getMethodName} (String id) throws Exception {
		${model.modelRecordModel.simpleClassName} ${model.modelRecordModel.variableName}= ${model.serviceModel.variableName}.${model.serviceModel.getMethodName}( id);
		return ResponseModel.newSuccess().setData(to${model.modelRecordVOModel.simpleClassName}(${model.modelRecordModel.variableName}));
	}
	
	/**
	 * page...
	 */
	@ResponseBody
	@RequestMapping("/${model.controllerModel.pageMethodName}")
	public ResponseModel ${model.controllerModel.pageMethodName}(${model.criteriaModel.simpleClassName} ${model.criteriaModel.variableName}, JSimplePageable simplePageable) throws Exception {
		JPage<${model.modelRecordModel.simpleClassName}> ${model.modelRecordModel.variableName}sPage=${model.serviceModel.variableName}.${model.serviceModel.pageMethodName}( ${model.criteriaModel.variableName},simplePageable);
		to${model.modelRecordVOModel.simpleClassName}Page(${model.modelRecordModel.variableName}sPage);
		return ResponseModel.newSuccess().setData(${model.modelRecordModel.variableName}sPage);
	}

</#list>
	
}
