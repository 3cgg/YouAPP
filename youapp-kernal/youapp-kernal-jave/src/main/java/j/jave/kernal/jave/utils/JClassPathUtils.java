package j.jave.kernal.jave.utils;

import java.io.File;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public abstract class JClassPathUtils {

	/**
	 * get all CLASSPATH files. 
	 * <li>.class</li>
	 * <li>directory</li>
	 * <li>.jar</li>
	 * @return
	 */
	public static Set<File> getClassPathFilesFromSystem(){
		Set<File> classPaths=new HashSet<File>();
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
