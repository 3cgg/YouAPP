/**
 * 
 */
package j.jave.framework.utils;

import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.logging.JLoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author J
 */
public abstract class JJARUtils {
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JJARUtils.class);
	
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
					try {
						classes.add(classLoader.loadClass(jarEntry.getName().replace(".class", "").replace("/", ".")));
					} catch (ClassNotFoundException e) {
						// never occurs. 
						LOGGER.warn("cannot load class", e);
					}
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
	
	
}
