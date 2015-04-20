package j.jave.framework._package;

import java.util.regex.Pattern;

/**
 * default configuration
 * @author J
 */
public class JPackageScanDefaultConfigure implements JPackageScanConfigure {

	/**
	 * class loader to load class. 
	 */
	protected ClassLoader classLoader; 
	
	/**
	 * scan under the package 
	 */
	protected String[] includePackages=new String[]{};
	
	/**
	 * scan a class of its name in the collections. 
	 */
	protected String[] includeClassNames=new String[]{};
	
	/**
	 * regular expression. 
	 */
	protected Pattern[] patterns=new Pattern[]{};
	
	public JPackageScanDefaultConfigure(){
		this.classLoader=Thread.currentThread().getContextClassLoader();
	}
	
	@Override
	public void setClassLoader(ClassLoader classLoader) {
		if(classLoader!=null){
			this.classLoader=classLoader;
		}
	}

	@Override
	public void setIncludePackages(String[] includePackages) {
		if(includePackages!=null){
			this.includePackages=new String[includePackages.length];
			for (int i = 0; i < includePackages.length; i++) {
				this.includePackages[i]=includePackages[i].replace(".", "/");
			}
		}
	}

	@Override
	public void setIncludeClassNames(String[] includeClassNames) {
		if(includeClassNames!=null){
			this.includeClassNames=includeClassNames;
		}
	}

	@Override
	public void setExpression(String[] expression) {
		if(expression!=null){
			patterns=new Pattern[expression.length];
			for(int i=0;i<expression.length;i++){
				patterns[i]=Pattern.compile(expression[i]);
			}
		}
	}

}
