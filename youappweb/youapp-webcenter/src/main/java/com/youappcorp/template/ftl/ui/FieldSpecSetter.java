package com.youappcorp.template.ftl.ui;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;
import com.youappcorp.template.ftl.KeyNames;
import com.youappcorp.template.ftl.ui.UIFieldSpec.UIDateFieldSpec;
import com.youappcorp.template.ftl.ui.UIFieldSpec.UITextFieldSpec;
import com.youappcorp.template.ftl.ui.UIFieldSpec.UITextareaFieldSpec;

public class FieldSpecSetter {
	
	private boolean isDesc(String property,ModelConfig modelConfig){
		return property.endsWith("Desc")
				||property.endsWith("Description")
				||property.endsWith("description");
	}

	public void setFieldSpec(UIField uiField, ModelConfig modelConfig) {
		if(KeyNames.FIELD_TYPE_NUMERIC.equals(uiField.getFieldType())){
			UITextFieldSpec textFieldSpec=new UITextFieldSpec();
			textFieldSpec.setFieldType(KeyNames.FIELD_SPEC_TEXT);
			uiField.setFieldSpec(textFieldSpec);
		}else if(KeyNames.FIELD_TYPE_DATE.equals(uiField.getFieldType())){
			UIDateFieldSpec dateFieldSpec=new UIDateFieldSpec();
			dateFieldSpec.setFieldType(KeyNames.FIELD_SPEC_DATE);
			uiField.setFieldSpec(dateFieldSpec);
		}else if(KeyNames.FIELD_TYPE_STRING.equals(uiField.getFieldType())){
			if(isDesc(uiField.getProperty(), modelConfig)){
				UITextareaFieldSpec textareaFieldSpec=new UITextareaFieldSpec();
				textareaFieldSpec.setFieldType(KeyNames.FIELD_SPEC_TEXTAREA);
				textareaFieldSpec.setMaxLength(2000);
				textareaFieldSpec.setRequired("false");
				uiField.setFieldSpec(textareaFieldSpec);
			}else {
				UITextFieldSpec textFieldSpec=new UITextFieldSpec();
				textFieldSpec.setFieldType(KeyNames.FIELD_SPEC_TEXT);
				uiField.setFieldSpec(textFieldSpec);
			}
		}
	}
	
}
