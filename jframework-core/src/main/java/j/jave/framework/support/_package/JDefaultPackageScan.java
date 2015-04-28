package j.jave.framework.support._package;

import j.jave.framework.utils.JClassPathUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * scan all sub-classes , extend or implements the interface or class. 
 * @author J
 */
public class JDefaultPackageScan extends JPackageScanDefaultConfiguration implements JPackageScan , JPackageScanConfigure {
	
	private final Class<?> clazz;
	
	/**
	 * @param clazz super-class, or interface
	 */
	public JDefaultPackageScan(Class<?> clazz) {
		this.clazz=clazz;
	}
	
	@Override
	public Set<Class<?>> scan() {
		
		ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
		Set<Class<?>> classes=new HashSet<Class<?>>();
		
		Set<File> files= JClassPathUtils.getClassPathFilesFromSystem();
		for (Iterator<File> iterator = files.iterator(); iterator.hasNext();) {
			File classPathFile =  iterator.next();
			if(classPathFile.exists()){
				String fileName=classPathFile.getName();
				
				if(fileName.endsWith(".jar")){
					JJARDefaultScan defaultScan=new JJARDefaultScan(classPathFile);
					defaultScan.setClassLoader(classLoader);
					defaultScan.setIncludePackages(includePackages);
					classes.addAll(JPackageResolve.get().getSubClass(defaultScan, clazz));
				}
				else{
					JFileClassPathDefaultScan defaultScan=new JFileClassPathDefaultScan(classPathFile);
					defaultScan.setClassLoader(classLoader);
					defaultScan.setIncludePackages(includePackages);
					classes.addAll(JPackageResolve.get().getSubClass(defaultScan, clazz));
				}
			}
		}
		
		return classes;
	}

	
}
