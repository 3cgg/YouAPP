package me.bunny.app._c._web.web.youappmvc.listener;

import me.bunny.app._c.sps.core.servicehub.SpringApplicationContextInitializedEvent;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

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
