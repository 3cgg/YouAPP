package j.jave.framework.support._package;

public interface JPackageScanConfigure {

	void setClassLoader(ClassLoader classLoader);
	
	/**
	 * 
	 * @param includePackages
	 */
	void setIncludePackages(String[] includePackages);
	
	/**
	 * filter on the passing includePackages
	 * @param includeClassNames
	 */
	void setIncludeClassNames(String[] includeClassNames);
	
	/**
	 * highest priority 
	 * @param expression
	 */
	void setExpression(String[] expression);
	
}
