package j.jave.kernal.jave.startup;

import j.jave.kernal.JConfiguration;

public interface JAheadProcessBeforeStartupService extends JStartupService {

	void aheadProcessBeforeStartup(JConfiguration configuration) throws Exception;
	
}
