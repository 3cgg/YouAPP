package com.youappcorp.template.ftl.ui.view;

import java.util.ArrayList;
import java.util.List;

import com.youappcorp.template.ftl.Config;
import com.youappcorp.template.ftl.Config.FieldConfig;
import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.ModelField;
import com.youappcorp.template.ftl.ModelModel;
import com.youappcorp.template.ftl.ui.Buckets;
import com.youappcorp.template.ftl.ui.FieldSpecSetter;

import me.bunny.kernel._c.utils.JObjectUtils;

public class UIDefaultViewFieldParser implements
		UIViewFieldParser {

	private boolean isSkip(ModelField modelField,ModelConfig modelConfig){
		return "id".equals(modelField.getProperty())
				||"createId".equals(modelField.getProperty())
				||"updateId".equals(modelField.getProperty())
				||"createTime".equals(modelField.getProperty())
				||"updateTime".equals(modelField.getProperty())
				||"deleted".equals(modelField.getProperty())
				||"version".equals(modelField.getProperty());
	}
	
	private boolean isTimeAppend(UIViewField viewField,ModelConfig modelConfig){
		return isTimeAppend(viewField.getProperty(), modelConfig);
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
	public List<UIViewField> parse(ModelConfig modelConfig)
			throws Exception {
		Config config=modelConfig.internalConfig().passConfig();
		List<UIViewField> viewFields=new ArrayList<UIViewField>();
		ModelModel modelModel=modelConfig.modelModel();
		for(ModelField modelField:modelModel.getModelFields()){
			if(!isSkip(modelField, modelConfig)){
				UIViewField viewField=JObjectUtils.simpleCopy(modelField, UIViewField.class);
				FieldConfig fieldConfig=config.getUIField(viewField.getProperty());
				viewField.setLabel(fieldConfig==null?viewField.getProperty():fieldConfig.getLabel());
				
				fillinFieldSpec(viewField, modelConfig);

				if(isDesc(viewField.getProperty(), modelConfig)){
					viewField.setColNum(8);
				}
				viewFields.add(viewField);
			}
		}
		
		Buckets buckets=new Buckets();
		for(UIViewField viewField:viewFields){
			buckets.addUIField(viewField, modelConfig);
		}
		return buckets.evenCollection();
	}

	private void fillinFieldSpec(UIViewField viewField, ModelConfig modelConfig) {
		new FieldSpecSetter().setFieldSpec(viewField, modelConfig);
	}
	

}
