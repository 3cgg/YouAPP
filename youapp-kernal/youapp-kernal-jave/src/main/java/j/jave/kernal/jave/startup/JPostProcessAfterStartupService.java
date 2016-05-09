package j.jave.kernal.jave.startup;

import j.jave.kernal.JConfiguration;

public interface JPostProcessAfterStartupService extends JStartupService {

	void postProcessAfterStartup(JConfiguration configuration) throws Exception;
	
}
