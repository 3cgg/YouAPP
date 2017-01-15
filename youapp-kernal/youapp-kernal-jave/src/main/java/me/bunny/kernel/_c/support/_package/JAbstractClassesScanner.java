package me.bunny.kernel._c.support._package;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;

public abstract class JAbstractClassesScanner extends JClassesScanDefaultConfiguration implements JClassesScanner{
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	protected final File file;
	
	public JAbstractClassesScanner(File file){
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
