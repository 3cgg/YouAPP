/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.utils.JUniqueUtils;

/**
 * an abstract class : the items below provided:
 * <li>1. default name.</li>
 * <li>2. default unique identification</li>
 * <li>3. default describer .</li>
 * @author J
 */
public abstract class JAbstractServiceFactory<T extends JService> implements JServiceFactory<T> {

	@Override
	public String getName() {
		return this.getClass().getName();
	}

	@Override
	public String getUniqueId() {
		return JUniqueUtils.unique();
	}
	@Override
	public String describer() {
		return getName()+","+getUniqueId();
	}
}
