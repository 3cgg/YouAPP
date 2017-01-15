/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;

import me.bunny.kernel.jave.service.JService;
import me.bunny.kernel.jave.utils.JUniqueUtils;

/**
 * an abstract class : the items below provided:
 * <li>1. default name.</li>
 * <li>2. default unique identification</li>
 * <li>3. default describer .</li>
 * <pre>the two service factory support including <code>JServiceFactorySupport</code> &  <code>SpringServiceFactorySupport</code>.
 * Any concrete service factory should extend from items above.
 * @author J
 * @see JServiceFactorySupport
 * @see SpringServiceFactorySupport
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
	
	protected boolean isCanRegister(){
		return true;
	}
	
	@Override
	public boolean available() {
		return true;
	}
}
