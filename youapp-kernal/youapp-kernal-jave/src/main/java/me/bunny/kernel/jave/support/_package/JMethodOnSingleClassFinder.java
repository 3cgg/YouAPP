/**
 * 
 */
package me.bunny.kernel.jave.support._package;

import me.bunny.kernel.jave.support._package.JMethodInfoProvider.JMethodInfoGen;

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
