package ${modelRecordModel.classPackage};

import j.jave.kernal.jave.utils.JObjectUtils;

import ${modelModel.className};

public class ${modelRecordModel.simpleClassName} extends ${modelModel.simpleClassName} {

	public ${modelModel.simpleClassName} to${modelModel.simpleClassName}(){
		return JObjectUtils.simpleCopy(this, ${modelModel.simpleClassName}.class);
	}
	
	
}
