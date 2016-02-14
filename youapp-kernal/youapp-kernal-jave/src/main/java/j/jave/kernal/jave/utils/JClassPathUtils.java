package j.jave.kernal.jave.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class JClassPathUtils {

	/**
	 * get all CLASSPATH files. 
	 * <li>.class</li>
	 * <li>directory</li>
	 * <li>.jar</li>
	 * @return
	 */
	public static List<File> getClassPathFilesFromSystem(){
		List<File> classPaths=new ArrayList<File>();
		Properties properties=System.getProperties();
		String javaClassPath=(String) properties.get("java.class.path");
		String[] paths=javaClassPath.split(";");
		for(int i=0;i<paths.length;i++){
			String path=paths[i];
			if(JStringUtils.isNotNullOrEmpty(path)){
				classPaths.add(new File(path));
			}
		}
		return classPaths;
	}
	
}
