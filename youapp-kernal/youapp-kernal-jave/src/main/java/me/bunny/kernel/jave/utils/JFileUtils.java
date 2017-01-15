package me.bunny.kernel.jave.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import me.bunny.kernel.jave.io.JClassRootPathResolver;
import me.bunny.kernel.jave.io.JPathResolver;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

public abstract class JFileUtils {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JFileUtils.class);
	
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
		return name.substring(dotIndex+1);
	}
	
	/**
	 * as same as {@link #getFile(JPathResolver)} with parameter {@link JClassRootPathResolver}
	 * @param fileName
	 * @return
	 */
	public static File getFileFromClassPath(String fileName){
		return getFile(new JClassRootPathResolver(fileName)); 
	}
	
	/**
	 * get actual file from the path which the {@link JPathResolver} resolves. 
	 * @param pathResolver
	 * @return
	 */
	public static File getFile(JPathResolver pathResolver){
		try {
			return new File(pathResolver.resolve());
		} catch (Exception e) {
			LOGGER.error(pathResolver.getDescription()+"not exists ", e);
			throw new JUtilException(e);  
		}
	}
	
	/**
	 * copy file.
	 * @param file
	 * @param fullPath
	 */
	public static void copyFile(File file, String fullPath) {
		try {
			FileOutputStream fos = null;
			FileInputStream fis = null;
			try {
				fos = new FileOutputStream(fullPath);
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
			} finally {
				if (fis != null) {
					fis.close();
				}
				if (fos != null) {
					fos.flush();
					fos.close();
				}
			}
		} catch (Exception e) {
			throw new JUtilException(e); 
		}
	}
	
	/**
	 * get file bytes. 
	 * @param file
	 * @return
	 */
	public static byte[] getBytes(File file) {
		try {

			if (file == null ){
				return null;
			}
			
			if(!file.exists()){
				throw new IllegalArgumentException("the file does not exist.");
			}
			
			if(file.exists()&&file.isDirectory()){
				throw new IllegalArgumentException("the file is directory : "+file.getPath());
			}

			ByteArrayOutputStream bos = null;
			FileInputStream fis = null;
			try {
				bos = new ByteArrayOutputStream();
				fis = new FileInputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					bos.write(buffer, 0, len);
				}
			} finally {
				if (fis != null) {
					fis.close();
				}
			}
			return bos.toByteArray();
		} catch (Exception e) {
			throw new JUtilException(e); 
		}
	}
	
	/**
	 * get file name according to the file path.
	 * @param filePath
	 * @return file name with extension.
	 */
	public static final String getFileName(String filePath){
		
		if(JStringUtils.isNullOrEmpty(filePath)) {
			throw new IllegalArgumentException(filePath+" is null or empty.");
		}
		
		int lastSlashIndex=filePath.lastIndexOf("/");
		
		if(lastSlashIndex==-1){
			return filePath;
		}
		
		return filePath.substring(lastSlashIndex+1);
		
	}
	
}
