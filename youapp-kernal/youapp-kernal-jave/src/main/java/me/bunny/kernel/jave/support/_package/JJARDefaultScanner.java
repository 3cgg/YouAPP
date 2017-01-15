package me.bunny.kernel.jave.support._package;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.utils.JCollectionUtils;

/**
 * scan all classes from <strong>a jar</strong> via passing a predefined ClassLoader, otherwise uses default class loader of current thread. 
 * @author J
 *
 */
public class JJARDefaultScanner extends JAbstractClassesScanner implements JClassesScanner{
	
	private Class<?>[] superClasses;
	
	public JJARDefaultScanner(File file) {
		super(file);
	}
	
	public JJARDefaultScanner(File file,Class<?>... superClasses) {
		super(file);
		this.superClasses=superClasses;
	}
	
	
	@Override
	protected Set<Class<?>> doScan() {
		JarFile file=null;
		try {
			Set<Class<?>> classes=new HashSet<Class<?>>();
			file=new JarFile(this.file);

			if(includePackages.length>0){
				boolean exists=false;
				for(int i=0;i<includePackages.length;i++){
					JarEntry jarEntry=file.getJarEntry(includePackages[i]);
					if(jarEntry!=null){
						exists=true;
					}
				}
				if(!exists){
					return classes;  // return empty . 
				}
			}
			
			Enumeration<JarEntry> jarEntries= file.entries();
			while(jarEntries.hasMoreElements()){
				JarEntry jarEntry=jarEntries.nextElement();
				String className=jarEntry.getName();
				if(className.endsWith(".class")&&matches(className)){
					String clazzName=jarEntry.getName().replace(".class", "").replace("/", ".");
					Class<?> clazz=JClassUtils.load(clazzName, classLoader);
					if(JCollectionUtils.hasInArray(superClasses)){
						for(Class<?> superClass:superClasses){
							if(JClassUtils.isAssignable(superClass, clazz, true)){
								classes.add(clazz);
								break;
							}
						}
					}
					else{
						classes.add(clazz);
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
	
}
