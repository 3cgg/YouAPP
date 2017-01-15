package me.bunny.kernel._c.support._resource;


/**
 * basic resource URI scanner.
 * @author J
 */
public abstract class JAbstractResourceURIScanner extends JResourceURIConfiguration implements JFilePathFilterConfig , JResourceURIScanner{
	
	/**
	 * if scan all children directories recursively.
	 */
	protected boolean recurse;
	
	/**
	 * if scan all children directories recursively.
	 */
	public void setRecurse(boolean recurse) {
		this.recurse = recurse;
	}
	
}
