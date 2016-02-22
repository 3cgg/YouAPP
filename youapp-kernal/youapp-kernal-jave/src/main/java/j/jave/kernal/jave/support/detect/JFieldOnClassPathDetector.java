/**
 * 
 */
package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.support._package.JClassesScanner;
import j.jave.kernal.jave.support._package.JDefaultClassesScanner;
import j.jave.kernal.jave.utils.JAssert;

/**
 * default scan all classes in the CLASSPATH to detect the field . 
 *<p> you also can define customized implementation of {@code JClassesScan}  to provide whose classes need be processed.
 * @see JDefaultClassesScanner
 * @see JClassesScanner
 * @author J
 * @param <T>  the same as generic of {@link JFieldInfoGen }
 */
public class JFieldOnClassPathDetector<T> extends JFieldDetector<T> {
	
	public JFieldOnClassPathDetector(JFieldInfoGen<T> fieldInfo) {
		this(defaultFieldFilter,fieldInfo);
	} 
	
	/**
	 * 
	 * @param fieldFilter implementation of {@link JFieldFilter}
	 * @param fieldInfo implementation of {@link JFieldInfoGen}
	 */
	public JFieldOnClassPathDetector(JFieldFilter fieldFilter,JFieldInfoGen<T> fieldInfo) {
		super(fieldFilter,fieldInfo);
	} 
	
	/**
	 * detect classes via {@link JDefaultClassesScanner }.
	 * @param superClass
	 * @param scanOnClasspath  only true accepted.
	 */
	public void detect(Class<?> superClass){
		JAssert.notNull(superClass, "null param not supported.");
		// initialize all 
		JDefaultClassesScanner defaultPackageScan = new JDefaultClassesScanner(
				superClass);
		defaultPackageScan.setIncludePackages(includePackages);
		defaultPackageScan.setExpression(expression);
		defaultPackageScan.setIncludeClassNames(includeClassNames);
		defaultPackageScan.setClassLoader(classLoader);
		setClassesScan(defaultPackageScan);
		super.detect(superClass);
	}
	
}
