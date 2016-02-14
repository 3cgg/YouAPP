package j.jave.kernal.filedistribute;

import j.jave.kernal.jave.service.JServiceAware;

public interface JFileDistServiceAware extends JServiceAware {

	void setFileDistService(JFileDistService fileDistService);
	
	JFileDistService getFileDistService( );
}
