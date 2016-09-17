package ${modelRecordVOModel.classPackage};

import j.jave.kernal.jave.utils.JObjectUtils;

import ${modelRecordModel.className};

public class ${modelRecordVOModel.simpleClassName} extends ${modelRecordModel.simpleClassName} {

	public ${modelRecordVOModel.simpleClassName} to${modelRecordVOModel.simpleClassName}(){
		return JObjectUtils.simpleCopy(this, ${modelRecordVOModel.simpleClassName}.class);
	}
	
	
}
