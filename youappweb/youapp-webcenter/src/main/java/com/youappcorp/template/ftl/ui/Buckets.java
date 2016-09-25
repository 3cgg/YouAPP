package com.youappcorp.template.ftl.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ModelField;

import j.jave.kernal.jave.utils.JStringUtils;

public class Buckets {

	LinkedHashMap<String, List<UIField>> buckets=new LinkedHashMap<String, List<UIField>>();
	
	public void addUIField(UIField uiField,ModelConfig modelConfig){
		String bucket=getBucket(uiField, modelConfig, buckets);
		if(JStringUtils.isNullOrEmpty(bucket)){
			List<UIField> fields=new ArrayList<UIField>();
			fields.add(uiField);
			buckets.put(uiField.getProperty(), fields);
		}
		else{
			buckets.get(bucket).add(uiField);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T extends UIField> List<T> evenCollection(){
		fillinVirtual();
		List<UIField> uiFields=new ArrayList<UIField>();
		for(Entry<String, List<UIField>> bucket:buckets.entrySet()){
			List<UIField> fields=bucket.getValue();
			uiFields.addAll(fields);
		}
		return (List<T>) uiFields;
	}
	
	private void fillinVirtual(){
		for(Entry<String, List<UIField>> bucket:buckets.entrySet()){
			List<UIField> addFields=bucket.getValue();
			if(addFields.size()==1){
				UIField addField=new UIField();
				addField.setVirtual(true);
				addFields.add(addField);
				continue;
			}
		}
	}
	
	
	private String getBucket(UIField uiField,ModelConfig modelConfig,LinkedHashMap<String, List<UIField>> buckets ){
		
		//time append
		if(isTimeAppend(uiField, modelConfig)){
			if(buckets.containsKey(uiField.getRelatedProperty())){
				return uiField.getRelatedProperty();
			}
			else{
				return null;
			}
		}
		//desc
		if(isDesc(uiField.getProperty(), modelConfig)){
			return null;
		}
		
		//common
		for(Entry<String, List<UIField>> bucket:buckets.entrySet()){
			String bucketName=bucket.getKey();
			List<UIField> addFields=bucket.getValue();
			if(addFields.size()>1){
				continue;
			}
			if(isDesc(bucketName, modelConfig)
				||isTimeAppend(bucketName, modelConfig)
				){
				continue;
			}
			for(UIField field:addFields){
				if(field.getColNum()>6){
					break;
				}
				return bucket.getKey();
			}
		}
		return null;
	}
	
	private boolean isSkip(ModelField modelField,ModelConfig modelConfig){
		return "id".equals(modelField.getProperty())
				||"createId".equals(modelField.getProperty())
				||"updateId".equals(modelField.getProperty())
				||"createTime".equals(modelField.getProperty())
				||"updateTime".equals(modelField.getProperty())
				||"deleted".equals(modelField.getProperty())
				||"version".equals(modelField.getProperty());
	}
	
	private boolean isTimeAppend(UIField uiField,ModelConfig modelConfig){
		return isTimeAppend(uiField.getProperty(), modelConfig);
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
	
}
