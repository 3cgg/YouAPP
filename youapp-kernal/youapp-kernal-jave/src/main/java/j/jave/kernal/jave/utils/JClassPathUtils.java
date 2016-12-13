package j.jave.kernal.jave.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.reflect.JClassPathList;

public abstract class JClassPathUtils {
	
	/**
	 * get all CLASSPATH files.  see java.class.path property.
	 * <li>.class</li>
	 * <li>directory</li>
	 * <li>.jar</li>
	 * @return
	 */
	public static List<File> getJavaClassPathFiles(){
		List<File> classPaths=new ArrayList<File>();
		for(String absolutePath:getJavaClassPathFileAbsolutePaths()){
			classPaths.add(new File(absolutePath));
		}
		return classPaths;
	}
	
	public static List<String> getJavaClassPathFileAbsolutePaths(){
		JClassPathList classPathList=new JClassPathList();
		Properties properties=System.getProperties();
		String javaClassPath=(String) properties.get("java.class.path");
		classPathList.add(javaClassPath);
		return classPathList.getFileAbsolutePaths();
	}
	
	public static JClassPathList getJavaClassPathList(){
		JClassPathList classPathList=new JClassPathList();
		Properties properties=System.getProperties();
		String javaClassPath=(String) properties.get("java.class.path");
		classPathList.add(javaClassPath);
		return classPathList;
	}
	
	public static JClassPathList getRuntimeClassPathList(){
		JClassPathList classPathList=new JClassPathList();
		try{
			// for web
			URL libUrl=Thread.currentThread().getContextClassLoader().getResource("../lib");
			File libFile=null;
			if(libUrl!=null){
				libFile=new File(libUrl.toURI());
				if(libFile.isFile()){
					libFile=null;
				}
			}
			String libFilePath=libFile==null?null:libFile.getAbsolutePath();
			
			URL rootUrl=Thread.currentThread().getContextClassLoader().getResource("");
			File classesFile=null;
			if(rootUrl!=null){
				classesFile=new File(rootUrl.toURI());
				if(classesFile.isFile()){
					classesFile=null;
				}
			}
			
			String classesFilePath=classesFile==null?null:classesFile.getAbsolutePath();
			
			List<String> classPathFiles=getJavaClassPathFileAbsolutePaths();
			for (Iterator<String> iterator = classPathFiles.iterator(); iterator
					.hasNext();) {
				String filePath = iterator.next();
				if(!filePath.equals(libFilePath) 
						&&!filePath.equals(classesFilePath)){
					classPathList.add(filePath);
				}
			} 
			if(libFile!=null) classPathList.add(libFilePath);
			if(classesFile!=null) classPathList.add(classesFilePath);
		}catch(Exception e){
			throw new JInitializationException(e);
		}
		return classPathList;
	}
	
	public static List<String> getRuntimeClassPathFileAbsolutePaths(){
		return getRuntimeClassPathList().getFileAbsolutePaths();
	}
	
	
	public static List<File> getRuntimeClassPathFiles(){
		List<File> classPaths=new ArrayList<File>();
		for(String absolutePath:getRuntimeClassPathFileAbsolutePaths()){
			classPaths.add(new File(absolutePath));
		}
		return classPaths;
	}
	
	
	
}
