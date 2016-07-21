package j.jave.web.htmlclient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public abstract class JFileUtils {

	public static String getFileNameNoExtension(File file){
		if(file==null) return null ;
		String name=file.getName();
		int dotIndex=name.lastIndexOf('.');
		return name.substring(0,dotIndex!=-1?dotIndex:name.length());
	}
	
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
			throw new RuntimeException(e); 
		}
	}
	
	
}
