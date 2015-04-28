package j.jave.framework.utils;

import j.jave.framework.io.JPathResolver;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
			throw new UtilException(e); 
		}
	}
	
	/**
	 * get actual file from the path which the {@link JPathResolver} resolves. 
	 * @param pathResolver
	 * @return
	 */
	public static File getFile(JPathResolver pathResolver){
		try {
			return new File(pathResolver.resolver());
		} catch (Exception e) {
			LOGGER.error(pathResolver.getDescription()+"not exists ", e);
			throw new UtilException(e);  
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
			throw new UtilException(e); 
		}
	}
	
	/**
	 * get file bytes. 
	 * @param file
	 * @return
	 */
	public static byte[] getBytes(File file) {
		try {

			if (file == null || !file.isFile())
				return null;

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
			throw new UtilException(e); 
		}
	}
	
	
}
