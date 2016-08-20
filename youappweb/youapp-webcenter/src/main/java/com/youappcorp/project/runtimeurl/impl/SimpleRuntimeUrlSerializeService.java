package com.youappcorp.project.runtimeurl.impl;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.utils.JFileUtils;
import j.jave.kernal.jave.utils.JIOUtils;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.youappcorp.project.runtimeurl.model.RuntimeUrlSerializable;
import com.youappcorp.project.runtimeurl.service.RuntimeUrlSerializeService;

public class SimpleRuntimeUrlSerializeService implements
		RuntimeUrlSerializeService {
	
	private String basePath=
			SimpleRuntimeUrlSerializeService.class.
			getClassLoader().getResource("").getPath();
	
	@Override
	public void serialize(RuntimeUrlSerializable runtimeUrl) throws Exception {
		String data=JJSON.get().formatObject(runtimeUrl);
		write(getSeparateFilePath(runtimeUrl.getUrl()), data);
	}
	
	
	private void write(String filePath,String data) throws Exception{
		File file=new File(filePath);
		if(!file.exists()){
			file.createNewFile();
		}
		JIOUtils.write(file, data.getBytes("utf-8"));
	}

	@Override
	public void serialize(Collection<RuntimeUrlSerializable> runtimeUrls) throws Exception {
		String data=JJSON.get().formatObject(runtimeUrls);
		write(getSummaryFilePath(), data);
	}

	
	private String getSeparateFilePath(String url){
		String fileName=url.replace('/', '_')+".json";
		String filePath=basePath+"/"+fileName;
		return filePath;
	}
	
	
	private String getSummaryFilePath(){
		String fileName="_all_urls.json";
		String filePath=basePath+"/"+fileName;
		return filePath;
	}

	@Override
	public Collection<RuntimeUrlSerializable> read() throws Exception {
		String filePath=getSummaryFilePath();
		File file=new File(filePath);
		byte[] data=null;
		if(file.exists()){
			data=JFileUtils.getBytes(new File(filePath));
			return JJSON.get().parse(new String(data,"utf-8"), new TypeReference<List<RuntimeUrlSerializable>>() {});
		}
		return Collections.EMPTY_LIST;
		
	}


	@Override
	public RuntimeUrlSerializable read(String url) throws Exception {
		String filePath=getSeparateFilePath(url);
		File file=new File(filePath);
		byte[] data=null;
		if(file.exists()){
			data=JFileUtils.getBytes(new File(filePath));
			return JJSON.get().parse(new String(data,"utf-8"), RuntimeUrlSerializable.class);
		}
		return null;
	}

}
