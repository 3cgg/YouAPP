/**
 * 
 */
package me.bunny.kernel._c.support._package;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * wrap classes to do the similar thing via the {@link JClassesScanner}.
 * @see {@link JDefaultClassesScanner}
 * @see {@link JJARDefaultScanner}
 * @see {@link JFileSystemDefaultScanner}
 * @author J
 */
public class JClassProvidedScanner extends JClassesScanDefaultConfiguration implements JClassesScanner {

	private final Class<?>[] clazzes;
	
	public JClassProvidedScanner(Class<?>... clazzes) {
		this.clazzes=clazzes;
	}
	
	@Override
	public Set<Class<?>> scan() {
		Set<Class<?>> classes=new HashSet<Class<?>>(1);
		Collections.addAll(classes, clazzes);
		return classes;
	}

}
