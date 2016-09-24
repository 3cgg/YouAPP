package com.youappcorp.template.ftl;

import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.jave.support._package.JDefaultFieldMeta;
import j.jave.kernal.jave.support._package.JFieldOnSingleClassFinder;

public class DefaultModelFieldParser implements ModelFieldParser {

	@Override
	public List<ModelField> parse(Class<?> clazz) throws Exception {
		JFieldOnSingleClassFinder<JDefaultFieldMeta> finder=new JFieldOnSingleClassFinder<JDefaultFieldMeta>(clazz);
		List<JDefaultFieldMeta> defaultFieldMetas= finder.find().getFieldInfos();
		List<ModelField> modelFields=new ArrayList<ModelField>();
		for(JDefaultFieldMeta defaultFieldMeta:defaultFieldMetas){
			ModelField modelField=new ModelField();
			modelField.setProperty(defaultFieldMeta.getFieldName());
			modelField.setColumn(defaultFieldMeta.getFieldName());
			modelField.setField(defaultFieldMeta.getField());
			modelField.setSetterMethodName(defaultFieldMeta.getSetterMethodName());
			modelField.setGetterMethodName(defaultFieldMeta.getGetterMethodName());
			modelField.setFieldType(TemplateUtil.type(defaultFieldMeta.getField()));
			modelField.setSourceType(KeyNames.SOURCE_TYPE_CLASS);
			modelFields.add(modelField);
		}
		return modelFields;
	}

}
