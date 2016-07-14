package j.jave.kernal.container._resource;

import j.jave.kernal.jave.service.JService;

import java.net.URI;

public interface JResourceProcessorService extends JService {

	public Object process(URI uri,Object object );
	
}
