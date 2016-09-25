package com.youappcorp.template.ftl.ui.edit;

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

public class UIDefaultEditFieldParser implements
		UIEditFieldParser {

	private boolean isSkip(ModelField modelField,ModelConfig modelConfig){
		return "id".equals(modelField.getProperty()) 
				||"createId".equals(modelField.getProperty())
				||"updateId".equals(modelField.getProperty())
				||"createTime".equals(modelField.getProperty())
				||"updateTime".equals(modelField.getProperty())
				||"deleted".equals(modelField.getProperty())
				||"version".equals(modelField.getProperty());
	}
	
	private boolean isTimeAppend(UIEditField editField,ModelConfig modelConfig){
		return isTimeAppend(editField.getProperty(), modelConfig);
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
	
	private boolean isHidden(String property,ModelConfig modelConfig){
		return property.endsWith("id");
	}
	
	
	@Override
	public List<UIEditField> parse(ModelConfig modelConfig)
			throws Exception {
		Config config=modelConfig.internalConfig().passConfig();
		List<UIEditField> editFields=new ArrayList<UIEditField>();
		ModelModel modelModel=modelConfig.modelModel();
		for(ModelField modelField:modelModel.getModelFields()){
			if(!isSkip(modelField, modelConfig)){
				UIEditField editField=JObjectUtils.simpleCopy(modelField, UIEditField.class);
				FieldConfig fieldConfig=config.getUIField(editField.getProperty());
				editField.setLabel(fieldConfig==null?editField.getProperty():fieldConfig.getLabel());
				editField.setHidden(isHidden(editField.getProperty(), modelConfig));
				
				fillinFieldSpec(editField, modelConfig);

				if(isDesc(editField.getProperty(), modelConfig)){
					editField.setColNum(8);
				}
				editFields.add(editField);
			}
		}
		
		Buckets buckets=new Buckets();
		for(UIEditField editField:editFields){
			buckets.addUIField(editField, modelConfig);
		}
		return buckets.evenCollection();
	}

	private void fillinFieldSpec(UIEditField editField, ModelConfig modelConfig) {
		new FieldSpecSetter().setFieldSpec(editField, modelConfig);
	}
	

}
