/**
 * 
 */
package me.bunny.kernel.jave.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

import me.bunny.kernel.jave.reflect.JClassUtils;

/**
 * @author J
 */
public abstract class JJARUtils {
	
	private static final String URI="jar:%s!/%s";
	
	public static String getURI(String jarPath,String innerFilePath){
		return String.format(URI, jarPath,innerFilePath);
	}
	
	/**
	 * load all classes in the package defined by {@param packageName} via the {@link ClassLoader} 
	 * @param jarFile
	 * @param packageName
	 * @param classLoader
	 * @return 
	 */
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
					String className=jarEntry.getName().replace(".class", "").replace("/", ".");
					classes.add(JClassUtils.load(className, classLoader));
				}
			}
			return classes;
		} catch (IOException e) {
			throw new JUtilException(e); 
		}finally{
			if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					throw new JUtilException(e); 
				}
			}
		}
	}
	
	/**
	 * extra the expected jar entries from the specified jar.
	 * @param jarFile
	 * @param entryNameRegex the name(path) regex to filter expected ones.
	 * @return
	 */
	public static Set<JarEntry> getJarEntries(String jarFile,String... entryNameRegex){
		Set<JarEntry> entries=new HashSet<JarEntry>();
		JarFile file=null;
		List<Pattern> patterns=new ArrayList<Pattern>(entryNameRegex.length);
		for(int i=0;i<entryNameRegex.length;i++){
			patterns.add(Pattern.compile(entryNameRegex[i]));
		}
		try {
			file=new JarFile(jarFile);
			Enumeration<JarEntry> jarEntries= file.entries();
			while(jarEntries.hasMoreElements()){
				JarEntry jarEntry=jarEntries.nextElement();
				String name=jarEntry.getName();
				boolean valid=false;
				for(int i=0;i<patterns.size();i++){
					if(patterns.get(i).matcher(name).matches()){
						valid=true;
						break;
					}
				}
				if(valid){
					entries.add(jarEntry);
				}
			}
		} catch (IOException e) {
			throw new JUtilException(e); 
		}finally{
			if(file!=null){
				try {
					file.close();
				} catch (IOException e) {
					throw new JUtilException(e); 
				}
			}
		}
		return entries;
	}
	
	
}
