package j.jave.kernal.jave.utils;

import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassPathList;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public abstract class JClassPathUtils {

	private static final JLogger LOGGER =JLoggerFactory.getLogger(JClassPathUtils.class);
	
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
			LOGGER.info("expected to find [WEB-INF/lib] : "+ (libUrl==null?"NULL":libUrl.toString()));
			File libFile=null;
			if(libUrl!=null){
				libFile=new File(libUrl.toURI());
				if(!libFile.isDirectory()){
					libFile=null;
				}
			}
			String libFilePath=libFile==null?null:libFile.getAbsolutePath();
			
			URI uri=Thread.currentThread().getContextClassLoader().getResource("").toURI();
			LOGGER.info("expected to find [WEB-INF/classes] : "+ (uri==null?"NULL":uri.toString()));
			File classesFile=new File(uri);
			if(!classesFile.isDirectory()){
				classesFile=null;
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
			LOGGER.error(e.getMessage(), e);
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
