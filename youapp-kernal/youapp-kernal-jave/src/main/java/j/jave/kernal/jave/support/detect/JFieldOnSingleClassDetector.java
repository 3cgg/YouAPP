/**
 * 
 */
package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.support._package.JClassProvidedScanner;
import j.jave.kernal.jave.support._package.JClassesScanner;
import j.jave.kernal.jave.support._package.JDefaultClassesScanner;

/**
 * default scan all classes in the CLASSPATH to detect the field . 
 *<p> you also can define customized implementation of {@code JClassesScan}  to provide whose classes need be processed.
 * @see JDefaultClassesScanner
 * @see JClassesScanner
 * @author J
 * @param <T>  the same as generic of {@link JFieldInfoGen }
 */
public class JFieldOnSingleClassDetector<T> extends JFieldDetector<T> {
	
	public JFieldOnSingleClassDetector(JFieldFilter fieldFilter, JFieldInfoGen<T> fieldInfo) {
		super(fieldFilter,fieldInfo);
	} 
	
	public JFieldOnSingleClassDetector(JFieldInfoGen<T> fieldInfo) {
		super(defaultFieldFilter,fieldInfo);
	} 
	/**
	 * detect classes via {@link JClassProvidedScanner }.
	 * @param clazz
	 */
	public void detect(Class<?>... clazz){
		JClassProvidedScanner classesScan = new JClassProvidedScanner(clazz);
		setClassesScan(classesScan);
		super.detect(clazz);
	}

	
}
