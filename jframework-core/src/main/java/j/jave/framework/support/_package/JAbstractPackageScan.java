package j.jave.framework.support._package;

import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.logging.JLoggerFactory;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public abstract class JAbstractPackageScan extends JPackageScanDefaultConfiguration implements JPackageScan{
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	protected final File file;
	
	public JAbstractPackageScan(File file){
		this.file=file;
	}
	
	/**
	 * default validate. 
	 * @param className
	 * @return
	 */
	protected boolean matches(String className){
		boolean isMatch=false;
		if(includePackages.length>0){
			for(int i=0;i<includePackages.length;i++){
				
				if(className.startsWith(includePackages[i])){
					isMatch=true;
					break;
				}
			}
		}
		
		if(includeClassNames.length>0){
			for(int i=0;i<includeClassNames.length;i++){
				if(className.substring(className.lastIndexOf('/')).replace(".class", "").contains(includeClassNames[i])){
					isMatch=true;
					break;
				}
			}
		}
		
		if(patterns.length>0){
			for(int i=0;i<patterns.length;i++){
				Pattern pattern=patterns[i];
				if(pattern.matcher(className).find()){
					isMatch=true;
					break;
				}
			}
		}
		
		return isMatch;
		
	}
	
	Set<Class<?>> classes=Collections.newSetFromMap(new HashMap<Class<?>, Boolean>());
	
	private boolean flag=false; 
	
	@Override
	public Set<Class<?>> scan() {
		if(flag){
			if(!this.classes.isEmpty()){
				Set<Class<?>> clazzes=new HashSet<Class<?>>();
				clazzes.addAll(classes);
				return clazzes;
			}
			else{
				return new HashSet<Class<?>>();
			}
		}
		else{
			Set<Class<?>> clazzes= doScan();
			flag=true;
			if(clazzes!=null&&!clazzes.isEmpty()){
				classes.addAll(clazzes);
			}
			return clazzes;
		}
		
	}

	protected abstract Set<Class<?>> doScan() ;
	
	
}
