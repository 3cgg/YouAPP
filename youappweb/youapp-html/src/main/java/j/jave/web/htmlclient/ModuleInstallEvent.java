/**
 * 
 */
package j.jave.web.htmlclient;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=ModuleInstallListener.class)
public class ModuleInstallEvent extends JYouAPPEvent<ModuleInstallEvent> {
	
	private final String moduleMeta;
	
	public ModuleInstallEvent(Object source,String moduleMeta) {
		super(source);
		this.moduleMeta=moduleMeta;
	}

	public ModuleInstallEvent(Object source,int priority ,String moduleMeta) {
		super(source,priority);
		this.moduleMeta=moduleMeta;
	}

	public String getModuleMeta() {
		return moduleMeta;
	}
}
