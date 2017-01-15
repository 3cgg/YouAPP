package me.bunny.kernel._c.support._package;

public interface JClassesScanConfig {

	/**
	 * the class loader to load the classes
	 * @param classLoader
	 */
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
