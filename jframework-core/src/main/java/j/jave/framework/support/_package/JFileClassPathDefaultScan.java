package j.jave.framework.support._package;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class JFileClassPathDefaultScan extends JAbstractPackageScan implements JPackageScan{
	
	public JFileClassPathDefaultScan(File file) {
		super(file);
	}
	
	@Override
	public Set<Class<?>> doScan() {
		try {
			Set<Class<?>> classes=new HashSet<Class<?>>();
			if(includePackages!=null){
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
				try {
					Class<?> clazz = classLoader.loadClass(className);
					classes.add(clazz);
				} catch (ClassNotFoundException e) {
					//never occurs
					LOGGER.error(e.getMessage(), e);
				}
			}
		}
	}
	
	
}
