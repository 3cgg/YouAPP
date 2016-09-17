package ${criteriaModel.classPackage};

import j.jave.platform.data.web.model.BaseCriteria;

public class ${criteriaModel.simpleClassName} extends BaseCriteria {

<#list criteriaModel.modelFields as modelField>
	private String ${modelField.property};
	
</#list>

<#list criteriaModel.modelFields as modelField>
	public String ${modelField.getterMethodName}() {
		return ${modelField.property};
	}

	public void ${modelField.setterMethodName}(String ${modelField.property}) {
		this.${modelField.property} = ${modelField.property};
	}
	
</#list>
	
}
