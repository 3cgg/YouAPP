package j.jave.framework.commons.support._resource;


/**
 * basic resource URI scanner.
 * @author J
 */
public abstract class JAbstractResourceURIScan extends JResourceURIConfiguration implements JFilePathFilterConfig , JResourceURIScan{
	
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
