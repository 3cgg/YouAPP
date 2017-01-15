package me.bunny.kernel.filedistribute;

import me.bunny.kernel._c.service.JServiceAware;

public interface JFileDistServiceAware extends JServiceAware {

	void setFileDistService(JFileDistService fileDistService);
	
	JFileDistService getFileDistService( );
}
