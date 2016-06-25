package j.jave.kernal.jave.support._package;

import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JCollectionUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * load all classes from <strong>a file directory</strong> via passing a predefined ClassLoader,otherwise uses current thread class loader.
 * @author J
 *
 */
public class JFileSystemDefaultScanner extends JAbstractClassesScanner implements JClassesScanner{
	
	private Class<?>[] superClasses;
	
	public JFileSystemDefaultScanner(File file) {
		super(file);
	}
	
	public JFileSystemDefaultScanner(File file,Class<?>... superClasses) {
		super(file);
		this.superClasses=superClasses;
	}
	
	@Override
	protected Set<Class<?>> doScan() {
		try {
			Set<Class<?>> classes=new HashSet<Class<?>>();
			if(JCollectionUtils.hasInArray(includePackages)){
				for (int i = 0; i < includePackages.length; i++) {
					String packg=includePackages[i];
					String packgPath=packg.replace(".", "/");
					File packFile=new File(file.getPath()+"/"+packgPath);
					loadClassFromFile(classes, packFile);
				}
			}
			else{
				loadClassFromFile(classes, file);
			}
			return classes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private void loadClassFromFile(Set<Class<?>> classes,File file){
		if(file.exists()){
			if(file.isDirectory()){
				File[] files=file.listFiles();
				for(int i=0;i<files.length;i++){
					File innerFile=files[i];
					loadClassFromFile(classes, innerFile);
				}
			}
			else{//file
				if(!file.getName().endsWith(".class")) return ;
				String fileURIPath= file.toURI().toString();
				String classPathPath=this.file.toURI().toString();
				String className=fileURIPath.substring(classPathPath.length()).replace("/", ".").replace(".class", "");
				Class<?> clazz = JClassUtils.load(className, classLoader);
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
	}
	
	
}
