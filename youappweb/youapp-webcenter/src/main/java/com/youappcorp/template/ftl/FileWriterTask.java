package com.youappcorp.template.ftl;

import j.jave.kernal.jave.utils.JIOUtils;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataHierarchy;
import j.jave.kernal.taskdriven.tkdd.JTaskMetadataOnTask;

import java.io.File;
import java.util.List;

@JTaskMetadataHierarchy
@JTaskMetadataOnTask
public class FileWriterTask extends TemplateTask{

	@Override
	public Object doRun() throws Exception {
		
		List<FileWrapper> fileWrappers= getInternalConfig().files();
		for(FileWrapper fileWrapper:fileWrappers){
			File file=fileWrapper.getFile();
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			JIOUtils.write(file, fileWrapper.getData());
		}
        return true;
	}

}
