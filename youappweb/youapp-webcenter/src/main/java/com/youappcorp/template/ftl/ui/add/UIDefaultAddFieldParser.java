package com.youappcorp.template.ftl.ui.add;

import java.util.ArrayList;
import java.util.List;

import com.youappcorp.template.ftl.Config;
import com.youappcorp.template.ftl.Config.FieldConfig;
import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ModelField;
import com.youappcorp.template.ftl.ModelModel;
import com.youappcorp.template.ftl.ui.Buckets;
import com.youappcorp.template.ftl.ui.FieldSpecSetter;

import j.jave.kernal.jave.utils.JObjectUtils;

public class UIDefaultAddFieldParser implements
		UIAddFieldParser {

	private boolean isSkip(ModelField modelField,ModelConfig modelConfig){
		return "id".equals(modelField.getProperty())
				||"createId".equals(modelField.getProperty())
				||"updateId".equals(modelField.getProperty())
				||"createTime".equals(modelField.getProperty())
				||"updateTime".equals(modelField.getProperty())
				||"deleted".equals(modelField.getProperty())
				||"version".equals(modelField.getProperty());
	}
	
	private boolean isTimeAppend(UIAddField addField,ModelConfig modelConfig){
		return isTimeAppend(addField.getProperty(), modelConfig);
	}
	
	private boolean isTimeAppend(String property,ModelConfig modelConfig){
		return property.endsWith("imeStart")
				||property.endsWith("imeEnd");
	}
	
	
	private boolean isDesc(String property,ModelConfig modelConfig){
		return property.endsWith("Desc")
				||property.endsWith("Description")
				||property.endsWith("description");
	}
	
	
	@Override
	public List<UIAddField> parse(ModelConfig modelConfig)
			throws Exception {
		Config config=modelConfig.internalConfig().passConfig();
		List<UIAddField> addFields=new ArrayList<UIAddField>();
		ModelModel modelModel=modelConfig.modelModel();
		for(ModelField modelField:modelModel.getModelFields()){
			if(!isSkip(modelField, modelConfig)){
				UIAddField addField=JObjectUtils.simpleCopy(modelField, UIAddField.class);
				FieldConfig fieldConfig=config.getUIField(addField.getProperty());
				addField.setLabel(fieldConfig==null?addField.getProperty():fieldConfig.getLabel());
				
				fillinFieldSpec(addField, modelConfig);

				if(isDesc(addField.getProperty(), modelConfig)){
					addField.setColNum(8);
				}
				addFields.add(addField);
			}
		}
		
		Buckets buckets=new Buckets();
		for(UIAddField addField:addFields){
			buckets.addUIField(addField, modelConfig);
		}
		return buckets.evenCollection();
	}

	private void fillinFieldSpec(UIAddField addField, ModelConfig modelConfig) {
		new FieldSpecSetter().setFieldSpec(addField, modelConfig);
	}
	

}
