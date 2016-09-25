package ${modelRecordModel.classPackage};

import j.jave.kernal.jave.utils.JObjectUtils;

import j.jave.platform.data.web.model.JInputModel;
import j.jave.platform.data.web.model.JOutputModel;

import ${modelModel.className};

public class ${modelRecordModel.simpleClassName} extends ${modelModel.simpleClassName} implements JInputModel , JOutputModel {

	public ${modelModel.simpleClassName} to${modelModel.simpleClassName}(){
		return JObjectUtils.simpleCopy(this, ${modelModel.simpleClassName}.class);
	}
	
	
}
