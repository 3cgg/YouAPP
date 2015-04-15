/**
 * 
 */
package j.jave.framework.utils;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Administrator
 *
 */
public class JJARUtils {

	
	public static Set<Class<?>> scan(File jarFile, String packageName,ClassLoader classLoader){
		JarFile file=null;
		try {
			Set<Class<?>> classes=new HashSet<Class<?>>();
			String pkName=packageName.replace(".", "/");
			file=new JarFile(jarFile);
			JarEntry packageEntry=file.getJarEntry(pkName);
			if(packageEntry==null) return classes;
			Enumeration<JarEntry> jarEntries= file.entries();
			while(jarEntries.hasMoreElements()){
				JarEntry jarEntry=jarEntries.nextElement();
				if(jarEntry.getName().endsWith(".class")&&jarEntry.getName().startsWith(pkName+"/")){
					try {
						classes.add(classLoader.loadClass(jarEntry.getName().replace(".class", "").replace("/", ".")));
					} catch (ClassNotFoundException e) {
						// never occurs. 
					}
				}
			}
			return classes;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}finally{
			if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		Set<Class<?>> classes= JJARUtils.scan(new File("D:\\java_\\so\\sources\\trunk\\jframework-core\\target\\jframework-core-1.0.jar"), "j",Thread.currentThread().getContextClassLoader());
		
		System.out.println(classes);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
