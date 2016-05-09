package j.jave.kernal.jave.startup;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.startup.eventlistener.JApplicationStartupServicePushEvent;
import j.jave.kernal.jave.startup.eventlistener.JApplicationStartupServicePushListener;

public abstract class JStartupServiceFactorySupport extends JServiceFactorySupport<JStartupService>
implements JApplicationStartupServicePushListener {
	
	private JStartupServiceChainService startupServiceChainService=
			JServiceHubDelegate.get().getService(this, JStartupServiceChainService.class);
	
	@Override
	public final Object trigger(JApplicationStartupServicePushEvent event) {
		startupServiceChainService.pushStartupService(getStartupService());
		return true;
	}
	
	protected abstract JStartupService getStartupService();

}
