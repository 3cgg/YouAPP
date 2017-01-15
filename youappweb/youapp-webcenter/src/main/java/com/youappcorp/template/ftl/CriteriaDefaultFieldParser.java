package com.youappcorp.template.ftl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import me.bunny.kernel._c.reflect.JClassUtils;
import me.bunny.kernel._c.utils.JObjectUtils;

public class CriteriaDefaultFieldParser implements CriteriaFieldParser{

	@Override
	public List<ModelField> parse(ModelConfig modelConfig) throws Exception {
		List<ModelField> modelFields= modelConfig.modelModel().getModelFields();
		List<ModelField> criteriaModelFields=new ArrayList<ModelField>();
		for(ModelField modelField:modelFields){
			ModelField criteriaModelField=JObjectUtils.simpleCopy(modelField, ModelField.class);
			if(Date.class.isAssignableFrom(criteriaModelField.getField().getType())){
				//start time
				ModelField appendModelField=new ModelField();
				appendModelField.setProperty(criteriaModelField.getProperty()+"Start");
				appendModelField.setSetterMethodName(JClassUtils.getSetterMethodName(appendModelField.getProperty()));
				appendModelField.setGetterMethodName(JClassUtils.getGetterMethodName(appendModelField.getProperty(), false));
				appendModelField.setFieldType(KeyNames.FIELD_TYPE_DATE);
				appendModelField.setSourceType(KeyNames.SOURCE_TYPE_APPEND);
				appendModelField.setSource(modelField);
				appendModelField.setRelatedProperty(criteriaModelField.getProperty()+"End");
				criteriaModelFields.add(appendModelField);
				//end time
				appendModelField=new ModelField();
				appendModelField.setProperty(criteriaModelField.getProperty()+"End");
				appendModelField.setSetterMethodName(JClassUtils.getSetterMethodName(appendModelField.getProperty()));
				appendModelField.setGetterMethodName(JClassUtils.getGetterMethodName(appendModelField.getProperty(), false));
				appendModelField.setFieldType(KeyNames.FIELD_TYPE_DATE);
				appendModelField.setSourceType(KeyNames.SOURCE_TYPE_APPEND);
				appendModelField.setSource(modelField);
				appendModelField.setRelatedProperty(criteriaModelField.getProperty()+"Start");
				criteriaModelFields.add(appendModelField);
			}
			criteriaModelField.setField(null);
			criteriaModelField.setSource(modelField);
			criteriaModelFields.add(criteriaModelField);
		}
		return criteriaModelFields;
	}
	
	
}
