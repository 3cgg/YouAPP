package com.youappcorp.template.ftl;

import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.youappcorp.template.ftl.InternalConfig.ModelConfig;

import freemarker.template.Template;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class RepoTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		ModelConfig modelConfig=getModelConfig();
		
		ModelModel modelModel=modelConfig.modelModel();
		
		RepoModel repoModel=new RepoModel();
		repoModel.setRepoPackage(modelConfig.internalConfig().repoPackage());
		repoModel.setRepoSimpleClassName(modelModel.getModelSimpleClassName()+"JPARepo");
		repoModel.setRepoClassName(repoModel.getRepoPackage()+"."+repoModel.getRepoSimpleClassName());
		modelConfig.setRepoModel(repoModel);
		
		/* Create a data-model */
        Map<String,Object> root = new HashMap<String, Object>();
        root.put("repoModel", repoModel);
        root.put("modelModel", modelModel);
        
        /* Get the template (uses cache internally) */
        Template temp = FtlConfig.get().getCfg().getTemplate("repo.ftl");
        /* Merge data-model with template */
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        Writer out = new OutputStreamWriter(byteArrayOutputStream);
        temp.process(root, out);
        String javaFileName=getInternalConfig().javaRelativePath()+"/"
        +repoModel.getRepoClassName().replace('.', '/')+".java";
        FileWrapper fileWrapper=new FileWrapper();
        fileWrapper.setFile(new File(javaFileName));
        fileWrapper.setData(byteArrayOutputStream.toByteArray());
        getInternalConfig().addFile(fileWrapper);
        
        return true;
	}

}
