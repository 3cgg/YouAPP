/**
 * 
 */
package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.support._package.JClassProvidedScanner;
import j.jave.kernal.jave.support._package.JClassesScanner;

/**
 * default scanner scanning field on the specified class.
 * @see JClassProvidedScanner
 * @see JClassesScanner
 * @author J
 * @param <T>  the same as generic of {@link JFieldInfoGen }
 */
public class JFieldOnSingleClassFinder<T> extends JAbstractFieldFinder<T> {
	
	private Class<?> clazz;
	
	public JFieldOnSingleClassFinder(Class<?> clazz) {
		this.clazz=clazz;
	}
	
	@Override
	protected JClassesScanner classesScanner() {
		return new JClassProvidedScanner(clazz);
	}

	
}
