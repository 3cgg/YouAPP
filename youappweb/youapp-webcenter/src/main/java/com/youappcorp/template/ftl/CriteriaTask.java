package com.youappcorp.template.ftl;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import freemarker.template.Template;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class CriteriaTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		ModelConfig modelConfig=getModelConfig();
		
		ModelModel modelModel=modelConfig.modelModel();
		
		CriteriaModel criteriaModel=new CriteriaModel();
		criteriaModel.setClassPackage(modelConfig.internalConfig().voPackage());
		criteriaModel.setSimpleClassName(modelModel.getSimpleClassName()+"Criteria");
		criteriaModel.setClassName(criteriaModel.getClassPackage()+"."
		+criteriaModel.getSimpleClassName());
		List<ModelField> modelFields= modelModel.getModelFields();
		List<ModelField> criteriaModelFields=new ArrayList<ModelField>();
		for(ModelField modelField:modelFields){
			ModelField criteriaModelField=JObjectUtils.simpleCopy(modelField, ModelField.class);
			if(Date.class.isAssignableFrom(criteriaModelField.getField().getType())){
				//start time
				ModelField appendModelField=new ModelField();
				appendModelField.setProperty(criteriaModelField.getProperty()+"Start");
				appendModelField.setSetterMethodName(JClassUtils.getSetterMethodName(appendModelField.getProperty()));
				appendModelField.setGetterMethodName(JClassUtils.getGetterMethodName(appendModelField.getProperty(), false));
				appendModelField.setFieldType(KeyNames.FIELD_TYPE_DATE);
				appendModelField.setSourceType(KeyNames.SOURCE_TYPE_APPEND);
				appendModelField.setSource(modelField);
				criteriaModelFields.add(appendModelField);
				//end time
				appendModelField=new ModelField();
				appendModelField.setProperty(criteriaModelField.getProperty()+"End");
				appendModelField.setSetterMethodName(JClassUtils.getSetterMethodName(appendModelField.getProperty()));
				appendModelField.setGetterMethodName(JClassUtils.getGetterMethodName(appendModelField.getProperty(), false));
				appendModelField.setFieldType(KeyNames.FIELD_TYPE_DATE);
				appendModelField.setSourceType(KeyNames.SOURCE_TYPE_APPEND);
				appendModelField.setSource(modelField);
				criteriaModelFields.add(appendModelField);
			}
			criteriaModelField.setField(null);
			criteriaModelField.setSource(modelField);
			criteriaModelFields.add(criteriaModelField);
		}
		criteriaModel.setModelFields(criteriaModelFields);
		
		modelConfig.setCriteriaModel(criteriaModel);
		
		/* Create a data-model */
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("modelModel", modelModel);
        root.put("criteriaModel", criteriaModel);
        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("criteria.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        String javaFileName=getInternalConfig().javaRelativePath()+"/"
        +criteriaModel.getClassName().replace('.', '/')+".java";
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(javaFileName));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
        
        return true;
	}

}
