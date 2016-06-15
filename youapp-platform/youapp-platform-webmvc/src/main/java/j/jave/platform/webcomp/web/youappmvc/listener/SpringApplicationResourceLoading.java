package j.jave.platform.webcomp.web.youappmvc.listener;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.sps.core.servicehub.SpringApplicationContextInitializedEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service
public class SpringApplicationResourceLoading implements ApplicationListener<ContextRefreshedEvent> {
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		SpringApplicationContextInitializedEvent
		 springApplicationContextInitializedEvent=new SpringApplicationContextInitializedEvent(this, JConfiguration.get());
		springApplicationContextInitializedEvent.setApplicationContext(event.getApplicationContext());
		JServiceHubDelegate.get().addImmediateEvent(springApplicationContextInitializedEvent);
	}

}
