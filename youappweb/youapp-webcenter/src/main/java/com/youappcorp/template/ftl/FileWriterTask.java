package com.youappcorp.template.ftl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import j.jave.kernal.taskdriven.tkdd.JTaskExecutionException;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;
import me.bunny.kernel._c.utils.JIOUtils;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class FileWriterTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		List<FileWrapper> fileWrappers= getInternalConfig().files();
		
		List<FileWrapper> targetFileWrappers= new ArrayList<>();
		for(FileWrapper fileWrapper:fileWrappers){
			if(getConfig().isJavaCode()){
				if(fileWrapper.getFile().getName().endsWith(".java")){
					targetFileWrappers.add(fileWrapper);
				}
			}
			if(getConfig().isUiCode()){
				if(fileWrapper.getFile().getName().endsWith(".html")){
					targetFileWrappers.add(fileWrapper);
				}
			}
		}
		
		for(FileWrapper fileWrapper:targetFileWrappers){
			File file=fileWrapper.getFile();
			if(file.exists()){
				throw new JTaskExecutionException("file already exists.["+file.getName()+"], please change your file name to another");
			}
		}
		
		for(FileWrapper fileWrapper:targetFileWrappers){
			File file=fileWrapper.getFile();
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			JIOUtils.write(file, fileWrapper.getData());
		}
        return true;
	}

}
