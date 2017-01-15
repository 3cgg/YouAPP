package me.bunny.kernel._c.reflect;

import java.util.ArrayList;
import java.util.List;

import me.bunny.kernel._c.support.JView;
import me.bunny.kernel._c.utils.JStringUtils;

public class JClassPathList implements JView{

	private List<String> fileAbsolutePaths=new ArrayList<String>();
	
	public boolean contains(String fileAbsolutePath){
		return fileAbsolutePaths.contains(fileAbsolutePath);
	}
	
	public void add(String fileAbsolutePath) {
		if(fileAbsolutePath.contains(";")){
			String[] paths=fileAbsolutePath.split(";");
			for(String path:paths){
				add(path);
			}
		}else if(fileAbsolutePath.contains(":")){
			String[] paths=fileAbsolutePath.split(":");
			for(String path:paths){
				add(path);
			}
		}else{
			if(JStringUtils.isNotNullOrEmpty(fileAbsolutePath)
					&&!fileAbsolutePaths.contains(fileAbsolutePath)){
				fileAbsolutePaths.add(fileAbsolutePath);
			}
		}
	}
	
	public void add(String... fileAbsolutePaths) {
		for (int i = 0; i < fileAbsolutePaths.length; i++) {
			add(fileAbsolutePaths[i]);
		}
	}
	
	@Override
	public String view() {
		StringBuffer stringBuffer=new StringBuffer();
		for (String fileAbsolutePath:fileAbsolutePaths) {
			stringBuffer.append(";");
			stringBuffer.append(fileAbsolutePath);
		}
		return stringBuffer.substring(1, stringBuffer.length());
	}
	
	public List<String> getFileAbsolutePaths() {
		return fileAbsolutePaths;
	}
	
	
	
	
	
	
	
	
	
}
