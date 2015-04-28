/**
 * 
 */
package j.jave.framework.support._package;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * wrap the classes to do something on the {@link JPackageScan}.
 * @see {@link JDefaultPackageScan}
 * @see {@link JJARDefaultScan}
 * @see {@link JFileClassPathDefaultScan}
 * @author J
 */
public class JClassProvidedScan extends JPackageScanDefaultConfiguration implements JPackageScan {

	private final Class<?>[] clazzes;
	
	public JClassProvidedScan(Class<?>... clazzes) {
		this.clazzes=clazzes;
	}
	
	@Override
	public Set<Class<?>> scan() {
		Set<Class<?>> classes=new HashSet<Class<?>>(1);
		Collections.addAll(classes, clazzes);
		return classes;
	}

}
