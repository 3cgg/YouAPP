package j.jave.framework.utils;

import j.jave.framework.io.JPathResolver;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class JFileUtils {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(JFileUtils.class);
	
	/**
	 * return file name without extension . i.e
	 * file name "JFileUtils" for "JFileUtils.java"
	 * @param file
	 * @return
	 */
	public static String getFileNameNoExtension(File file){
		if(file==null) return null ;
		String name=file.getName();
		int dotIndex=name.lastIndexOf('.');
		return name.substring(0,dotIndex!=-1?dotIndex:name.length());
	}
	
	/**
	 * return file extension . i.e
	 * extension name "java" for "JFileUtils.java"
	 * @param file
	 * @return
	 */
	public static String getFileExtension(File file){
		if(file==null) return null ;
		String name=file.getName();
		int dotIndex=name.lastIndexOf('.');
		if(dotIndex==-1){
			return "";
		}
		return name.substring(name.lastIndexOf('.'));
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	@Deprecated
	public static File getFileFromClassPath(String fileName){
		String classPath=Thread.currentThread().getContextClassLoader().getResource("").toString();
		try {
			URL url=Thread.currentThread().getContextClassLoader().getResource(fileName);
			if(url==null){
				LOGGER.warn(fileName+" not exists in  "+classPath);
				return null;
			}
			return new File(url.toURI());
		} catch (URISyntaxException e) {
			LOGGER.error(fileName+"not exists in  "+classPath, e);
		}
		return null;
	}
	
	public static File getFile(JPathResolver pathResolver){
		try {
			return new File(pathResolver.resolver());
		} catch (Exception e) {
			LOGGER.error(pathResolver.getDescription()+"not exists ", e);
		}
		return null;
	}
	
	
	
	
	
}
