package com.youappcorp.template.ftl;

import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.kernal.jave.utils.JStringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

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
	
	
	private boolean isDesc(String property,ModelConfig modelConfig){
		return property.endsWith("Desc")
				||property.endsWith("Description");
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
				if(isDesc(fieldConfig.getProperty(), modelConfig)){
					criteriaField.setColNum(12);
				}
				criteriaFields.add(criteriaField);
			}
		}
		
		LinkedHashMap<String, List<UIListCriteriaField>> buckets=new LinkedHashMap<String, List<UIListCriteriaField>>();
		for(UIListCriteriaField criteriaField:criteriaFields){
			String bucket=getBucket(criteriaField, modelConfig, buckets);
			if(JStringUtils.isNullOrEmpty(bucket)){
				List<UIListCriteriaField> fields=new ArrayList<UIListCriteriaField>();
				fields.add(criteriaField);
				buckets.put(criteriaField.getProperty(), fields);
			}
			else{
				buckets.get(bucket).add(criteriaField);
			}
		}
		
		fillinVirtual(buckets);
		
		return toCriteriaFields(buckets);
	}
	
	
	private List<UIListCriteriaField> toCriteriaFields(LinkedHashMap<String, List<UIListCriteriaField>> buckets){
		List<UIListCriteriaField> criteriaFields=new ArrayList<UIListCriteriaField>();
		for(Entry<String, List<UIListCriteriaField>> bucket:buckets.entrySet()){
			List<UIListCriteriaField> fields=bucket.getValue();
			criteriaFields.addAll(fields);
		}
		return criteriaFields;
	}
	
	private void fillinVirtual(LinkedHashMap<String, List<UIListCriteriaField>> buckets){
		for(Entry<String, List<UIListCriteriaField>> bucket:buckets.entrySet()){
			List<UIListCriteriaField> criteriaFields=bucket.getValue();
			if(criteriaFields.size()==1){
				UIListCriteriaField criteriaField=new UIListCriteriaField();
				criteriaField.setVirtual(true);
				criteriaFields.add(criteriaField);
				continue;
			}
		}
	}
	
	private String getBucket(UIListCriteriaField criteriaField,ModelConfig modelConfig,LinkedHashMap<String, List<UIListCriteriaField>> buckets ){
		
		//time append
		if(isTimeAppend(criteriaField, modelConfig)){
			if(buckets.containsKey(criteriaField.getRelatedProperty())){
				return criteriaField.getRelatedProperty();
			}
			else{
				return null;
			}
		}
		//desc
		if(isDesc(criteriaField.getProperty(), modelConfig)){
			return null;
		}
		
		//common
		for(Entry<String, List<UIListCriteriaField>> bucket:buckets.entrySet()){
			String bucketName=bucket.getKey();
			List<UIListCriteriaField> criteriaFields=bucket.getValue();
			if(criteriaFields.size()>1){
				continue;
			}
			if(isDesc(bucketName, modelConfig)
				||isTimeAppend(bucketName, modelConfig)
				){
				continue;
			}
			for(UIListCriteriaField field:criteriaFields){
				if(field.getColNum()>6){
					break;
				}
				return bucket.getKey();
			}
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	

}
