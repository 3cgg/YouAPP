package j.jave.framework._package;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JJARDefaultScan extends JAbstractPackageScan implements JPackageScan{
	
	public JJARDefaultScan(File file) {
		super(file);
	}
	
	@Override
	public Set<Class<?>> doScan() {
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
					try {
						classes.add(classLoader.loadClass(jarEntry.getName().replace(".class", "").replace("/", ".")));
					} catch (ClassNotFoundException e) {
						LOGGER.error(e.getMessage(), e);
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
	
}
