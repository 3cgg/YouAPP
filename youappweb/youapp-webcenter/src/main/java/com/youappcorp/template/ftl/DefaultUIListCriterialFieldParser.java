package com.youappcorp.template.ftl;

import j.jave.kernal.jave.utils.JObjectUtils;

import java.util.ArrayList;
import java.util.List;

import com.youappcorp.template.ftl.Config.FieldConfig;
import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

public class DefaultUIListCriterialFieldParser implements
		UIListCriterialFieldParser {

	@Override
	public List<UIListCriteriaField> parse(ModelConfig modelConfig)
			throws Exception {
		Config config=modelConfig.internalConfig().passConfig();
		List<UIListCriteriaField> criteriaFields=new ArrayList<UIListCriteriaField>();
		CriteriaModel criteriaModel=modelConfig.criteriaModel();
		for(ModelField modelField:criteriaModel.getModelFields()){
			UIListCriteriaField criteriaField=JObjectUtils.simpleCopy(modelField, UIListCriteriaField.class);
			FieldConfig fieldConfig=config.getUICriteriaField(criteriaField.getProperty());
			criteriaField.setLabel(fieldConfig==null?criteriaField.getProperty():fieldConfig.getLabel());
			
		}
		
		
		
		
		
		
		
		
		
		
		
		return null;
	}

}
