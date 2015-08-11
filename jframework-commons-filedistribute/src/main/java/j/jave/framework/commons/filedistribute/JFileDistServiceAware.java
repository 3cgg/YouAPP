package j.jave.framework.commons.filedistribute;

import j.jave.framework.commons.service.JServiceAware;

public interface JFileDistServiceAware extends JServiceAware {

	void setFileDistService(JFileDistService fileDistService);
	
	JFileDistService getFileDistService( );
}
