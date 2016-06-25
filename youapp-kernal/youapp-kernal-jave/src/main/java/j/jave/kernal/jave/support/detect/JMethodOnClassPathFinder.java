/**
 * 
 */
package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.support._package.JClassesScanner;
import j.jave.kernal.jave.support._package.JDefaultClassesScanner;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider.JMethodInfoGen;

/**
 * default scan all classes that are sub-classes of some other classes {@link #superClasses}
 * in the CLASSPATH , to get all methods. 
 * @see JDefaultClassesScanner
 * @see JClassesScanner
 * @author J
 * @param <T>  the same as generic of {@link JMethodInfoGen }
 */
public class JMethodOnClassPathFinder<T> extends JAbstractMethodFinder<T> {
	
	private Class<?>[] superClasses;
	
	public JMethodOnClassPathFinder(Class<?>... superClasses) {
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
