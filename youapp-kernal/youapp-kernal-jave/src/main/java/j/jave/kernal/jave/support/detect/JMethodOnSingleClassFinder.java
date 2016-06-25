/**
 * 
 */
package j.jave.kernal.jave.support.detect;

import j.jave.kernal.jave.support._package.JClassProvidedScanner;
import j.jave.kernal.jave.support._package.JClassesScanner;
import j.jave.kernal.jave.support.detect.JMethodInfoProvider.JMethodInfoGen;

/**
 * default scanner scanning method on the specified class.
 * @see JClassProvidedScanner
 * @see JClassesScanner
 * @author J
 * @param <T>  the same as generic of {@link JMethodInfoGen }
 */
public class JMethodOnSingleClassFinder<T> extends JAbstractMethodFinder<T> {
	
	private Class<?> clazz;
	
	public JMethodOnSingleClassFinder(Class<?> clazz) {
		this.clazz=clazz;
	}
	
	@Override
	protected JClassesScanner classesScanner() {
		return new JClassProvidedScanner(clazz);
	}

	
}
