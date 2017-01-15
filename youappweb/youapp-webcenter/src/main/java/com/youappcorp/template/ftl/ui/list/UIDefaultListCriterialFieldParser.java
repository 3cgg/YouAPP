package com.youappcorp.template.ftl.ui.list;

import java.util.ArrayList;
import java.util.List;

import com.youappcorp.template.ftl.Config;
import com.youappcorp.template.ftl.Config.FieldConfig;
import com.youappcorp.template.ftl.CriteriaModel;
import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ModelField;
import com.youappcorp.template.ftl.ui.Buckets;
import com.youappcorp.template.ftl.ui.FieldSpecSetter;

import me.bunny.kernel._c.utils.JObjectUtils;

public class UIDefaultListCriterialFieldParser implements
		UIListCriterialFieldParser {

	private boolean isSkip(ModelField modelField,ModelConfig modelConfig){
		return "id".equals(modelField.getProperty())
				||"createId".equals(modelField.getProperty())
				||"updateId".equals(modelField.getProperty())
				||"createTime".equals(modelField.getProperty())
				||"updateTime".equals(modelField.getProperty())
				||"deleted".equals(modelField.getProperty())
				||"version".equals(modelField.getProperty());
	}
	
	private boolean isTimeAppend(UIListCriteriaField criteriaField,ModelConfig modelConfig){
		return isTimeAppend(criteriaField.getProperty(), modelConfig);
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
	
	private void fillinFieldSpec(UIListCriteriaField criteriaField, ModelConfig modelConfig) {
		new FieldSpecSetter().setFieldSpec(criteriaField, modelConfig);
	}
	
	@Override
	public List<UIListCriteriaField> parse(ModelConfig modelConfig)
			throws Exception {
		Config config=modelConfig.internalConfig().passConfig();
		List<UIListCriteriaField> criteriaFields=new ArrayList<UIListCriteriaField>();
		CriteriaModel criteriaModel=modelConfig.criteriaModel();
		for(ModelField modelField:criteriaModel.getModelFields()){
			if(!isSkip(modelField, modelConfig)){
				UIListCriteriaField criteriaField=JObjectUtils.simpleCopy(modelField, UIListCriteriaField.class);
				FieldConfig fieldConfig=config.getUIField(criteriaField.getProperty());
				criteriaField.setLabel(fieldConfig==null?criteriaField.getProperty():fieldConfig.getLabel());
				
				fillinFieldSpec(criteriaField, modelConfig);
				
				if(isDesc(criteriaField.getProperty(), modelConfig)){
					criteriaField.setColNum(8);
				}
				criteriaFields.add(criteriaField);
			}
		}
		
		Buckets buckets=new Buckets();
		
		for(UIListCriteriaField criteriaField:criteriaFields){
			buckets.addUIField(criteriaField, modelConfig);
		}
		
		return buckets.evenCollection();
	}

}
