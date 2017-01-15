/**
 * 
 */
package me.bunny.kernel._c.support._package;


/**
 * default scan all classes that are sub-classes of some other classes {@link #superClasses}
 * in the CLASSPATH , to get all fields. 
 * @see JDefaultClassesScanner
 * @see JClassesScanner
 * @author J
 * @param <T>  the same as generic of {@link JFieldInfoGen }
 */
public class JFieldOnClassPathFinder<T> extends JAbstractFieldFinder<T> {
	
	private Class<?>[] superClasses;
	
	public JFieldOnClassPathFinder(Class<?>... superClasses) {
		this.superClasses=superClasses;
	}
	
	
	/**
	 * use {@link JDefaultClassesScanner}
	 */
	@Override
	protected JClassesScanner classesScanner() {
		JDefaultClassesScanner defaultClassesScanner = new JDefaultClassesScanner(
				this.superClasses);
		defaultClassesScanner.setIncludePackages(includePackages);
		defaultClassesScanner.setExpression(expression);
		defaultClassesScanner.setIncludeClassNames(includeClassNames);
		defaultClassesScanner.setClassLoader(classLoader);
		return defaultClassesScanner;
	}
	
	
	
	
}
