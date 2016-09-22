package com.youappcorp.template.ftl;

import j.jave.kernal.jave.utils.JObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youappcorp.template.ftl.Config.FieldConfig;
import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

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
	
	
	@Override
	public List<UIListCriteriaField> parse(ModelConfig modelConfig)
			throws Exception {
		Config config=modelConfig.internalConfig().passConfig();
		List<UIListCriteriaField> criteriaFields=new ArrayList<UIListCriteriaField>();
		CriteriaModel criteriaModel=modelConfig.criteriaModel();
		for(ModelField modelField:criteriaModel.getModelFields()){
			if(!isSkip(modelField, modelConfig)){
				UIListCriteriaField criteriaField=JObjectUtils.simpleCopy(modelField, UIListCriteriaField.class);
				FieldConfig fieldConfig=config.getUICriteriaField(criteriaField.getProperty());
				criteriaField.setLabel(fieldConfig==null?criteriaField.getProperty():fieldConfig.getLabel());
				criteriaFields.add(criteriaField);
			}
		}
		
		Map<String, List<UIListCriteriaField>> temp=new HashMap<String, List<UIListCriteriaField>>();
		for(UIListCriteriaField criteriaField:criteriaFields){
			if(isTimeAppend(criteriaField, modelConfig)){
				if(temp.containsKey(criteriaField.getRelatedProperty())){
					temp.get(criteriaField.getRelatedProperty()).add(criteriaField);
				}
				else{
					temp.put(criteriaField.getProperty(), criteriaFields);
				}
			}else{
				
				
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		return null;
	}

}
