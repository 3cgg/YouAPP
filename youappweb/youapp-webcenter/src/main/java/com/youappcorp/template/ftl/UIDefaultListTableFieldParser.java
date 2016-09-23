package com.youappcorp.template.ftl;

import j.jave.kernal.jave.utils.JObjectUtils;

import java.util.ArrayList;
import java.util.List;

import com.youappcorp.template.ftl.Config.FieldConfig;
import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

public class UIDefaultListTableFieldParser implements
		UIListTableFieldParser {

	private boolean isSkip(ModelField modelField,ModelConfig modelConfig){
		String property=modelField.getProperty();
		return "id".equals(property)
				||"createId".equals(property)
				||"updateId".equals(property)
				||"createTime".equals(property)
				||"updateTime".equals(property)
				||"deleted".equals(property)
				||"version".equals(property)
				||isTimeAppend(property, modelConfig);
	}
	
	private boolean isTimeAppend(String property,ModelConfig modelConfig){
		return property.endsWith("imeStart")
				||property.endsWith("imeEnd");
	}
	
	@Override
	public List<UIListTableField> parse(ModelConfig modelConfig)
			throws Exception {
		Config config=modelConfig.internalConfig().passConfig();
		List<UIListTableField> tableFields=new ArrayList<UIListTableField>();
		ModelModel modelModel=modelConfig.modelModel();
		for(ModelField modelField:modelModel.getModelFields()){
			if(!isSkip(modelField, modelConfig)){
				UIListTableField tableField=JObjectUtils.simpleCopy(modelField, UIListTableField.class);
				FieldConfig fieldConfig=config.getUIField(tableField.getProperty());
				tableField.setLabel(fieldConfig==null?tableField.getProperty():fieldConfig.getLabel());
				tableFields.add(tableField);
			}
		}
		
		return tableFields;
	}

}
